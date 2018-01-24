package src;

import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;
import javafx.util.Pair;
import src.base.Coin;
import src.base.Exchange;
import src.base.Order;
import src.binance.BinanceConnector;
import src.binance.data.ExchangeConnector;
import src.bitfinex.BitfinexConnector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static src.base.Coin.BTC;
import static src.base.Coin.IOTA;

public class Processor {

    public static final double THRESHOLD = 1.0;
    private ExchangeConnector binanceConnector;
    private ExchangeConnector bitfinexConnector;

    public Processor(BinanceConnector binanceConnector, BitfinexConnector bitfinexConnector) {
        this.binanceConnector = binanceConnector;
        this.bitfinexConnector = bitfinexConnector;
    }

    private Pair<Exchange, Exchange> getCheapAndExpensive(Coin coin, Exchange... exchanges) {
        Exchange cheap = null;
        Exchange expensive = null;
        double cheapestPrice = Double.MAX_VALUE;
        double mostExpensivePrice = Double.MIN_VALUE;
        for (Exchange exchange : exchanges) {
            double askPrice = exchange.getOrderBook(coin).asks.get(0).getPrice();
            if (askPrice < cheapestPrice) {
                cheapestPrice = askPrice;
                cheap = exchange;
            }
            double bidPrice = exchange.getOrderBook(coin).bids.get(0).getPrice();
            if (bidPrice > mostExpensivePrice) {
                mostExpensivePrice = bidPrice;
                expensive = exchange;
            }
        }

        return new Pair<>(cheap, expensive);
    }

    public Exchange simulateOperation(Coin coin, Exchange... exchanges) {
        long start = System.currentTimeMillis();
        Pair<Exchange, Exchange> cheapAndExpensive = getCheapAndExpensive(coin, exchanges);
        Exchange cheapest = cheapAndExpensive.getKey();
        Exchange expensive = cheapAndExpensive.getValue();
        if (cheapest == null || expensive == null || cheapest == expensive) {
            return null;
        }
        double maxToTrade = Collections.min(Arrays.asList(cheapest.getWalletAmount(coin),
                expensive.getWalletAmount(coin)));
        double btcMaxToTrade = Collections.min(Arrays.asList(cheapest.getWalletAmount(BTC),
                expensive.getWalletAmount(BTC)));

        double initialBtcSum = Exchange.getSumInWallets(BTC, exchanges);

        int buyBookCount = 0;
        int sellBookCount = 0;
        boolean retry = true;

        List<Order> asks = new ArrayList(cheapest.getOrderBook(coin).asks);
        List<Order> bids = new ArrayList(expensive.getOrderBook(coin).bids);
        Order orderToBuy = asks.get(buyBookCount);
        Order orderToSell = bids.get(sellBookCount);

        List<Order> bidsToExecute = new ArrayList<>();
        List<Order> asksToExecute = new ArrayList<>();
        int lastI = 0;
        double coinsAvailableToTrade = maxToTrade;
        double btcAvailableToTrade = btcMaxToTrade;
        for (Order bid : bids) {
            int i = lastI;
            while (i < asks.size()) {
                Order ask = asks.get(i);
                Log.debug(bid + " to ask: " + ask);
                double percentage = (bid.getPrice() / ask.getPrice() - 1) * 100;
                if (percentage < THRESHOLD) {
                    lastI = -1;
                    break;
                }
                Log.debug(percentage + "%");
                double askAmount = ask.getAmount();
                double bidAmount = bid.getAmount();
                if (bidAmount > askAmount) {
                    i++;
                    lastI = i;
                    if (askAmount > coinsAvailableToTrade) {
                        askAmount = coinsAvailableToTrade;
                        lastI = -1;
                    }
                    Order askToExecute = new Order(ask.getPrice(), askAmount);
                    double btcAmount = askToExecute.getBtcAmount();
                    if (btcAmount > btcAvailableToTrade) {
                        btcAmount = btcAvailableToTrade;
                        askAmount = btcAmount / askToExecute.getPrice();
                        askToExecute.setAmount(askAmount);
                    }
                    btcAvailableToTrade -= btcAmount;
                    asksToExecute.add(askToExecute);
                    bidsToExecute.add(new Order(bid.getPrice(), askAmount));
                    coinsAvailableToTrade -= askAmount;
                    Log.debug("Using all of ask");
                } else {
                    Log.debug("Using all of bid");
                    lastI = i;
                    if (bidAmount > coinsAvailableToTrade) {
                        bidAmount = coinsAvailableToTrade;
                        lastI = -1;
                    }
                    Order askToExecute = new Order(ask.getPrice(), bidAmount);
                    double btcAmount = askToExecute.getBtcAmount();
                    if (btcAmount > btcAvailableToTrade) {
                        btcAmount = btcAvailableToTrade;
                        bidAmount = btcAmount / askToExecute.getPrice();
                        askToExecute.setAmount(bidAmount);
                    }
                    btcAvailableToTrade -= btcAmount;
                    asksToExecute.add(askToExecute);
                    bidsToExecute.add(new Order(bid.getPrice(), bidAmount));
                    asks.set(i, new Order(ask.getPrice(), askAmount - bidAmount));
                    coinsAvailableToTrade -= bidAmount;
                    break;
                }
            }
            if (lastI < 0) {
                break;
            }
        }
        long end = System.currentTimeMillis();
        double btcSum = 0;
        double coinSum = 0;
        for (Order order : bidsToExecute) {
            double btcAmount = order.getBtcAmount();
            btcSum += btcAmount;
            coinSum += order.getAmount();
            Log.debug(order.toString() + " - " + btcAmount);
        }
        Log.debug("BTC: " + btcSum + " - COIN: " + coinSum);
        btcSum = 0;
        coinSum = 0;
        for (Order order : asksToExecute) {
            double btcAmount = order.getBtcAmount();
            btcSum += btcAmount;
            coinSum += order.getAmount();
            Log.debug(order.toString() + " - " + order.getBtcAmount());
        }
        Log.debug("BTC: " + btcSum + " - COIN: " + coinSum);
//        Observable.fromIterable(bidsToExecute).forEach(order -> Log.debug(order.toString()));

//        double coinBought = processTrade(coin, cheapest, expensive, orderToBuy, orderToSell, initialBtcSum);
//        if (Math.abs(coinBought - orderToBuy.getAmount()) < 0.0001) {
//            Log.debug("Exchange: " + cheapest + " - " + coinBought + " --- " + orderToBuy.getAmount());
//            buyBookCount += 1;
//            orderToBuy = asks.get(buyBookCount);
//            orderToSell = new Order(orderToSell.getPrice(), orderToSell.getAmount() - coinBought);
//        } else if (Math.abs(coinBought - orderToSell.getAmount()) < 0.0001) {
//            Log.debug("Exchange: " + expensive + " - " + coinBought + " --- " + orderToSell.getAmount());
//            sellBookCount += 1;
//            orderToBuy = new Order(orderToBuy.getPrice(), orderToBuy.getAmount() - coinBought);
//            orderToSell = bids.get(sellBookCount);
//        } else {
//            Log.print("Minimum is from wallet! Needs to be rebalanced");
//            retry = false;
//        }
//        if (retry) {
//            coinBought = processTrade(coin, cheapest, expensive, orderToBuy, orderToSell, initialBtcSum);
//        }
        Log.debug("Time spent: " + (end - start) + "ms");
        return cheapest;
    }

    private double processTrade(Coin coin, Exchange cheapest, Exchange expensive, Order orderToBuy, Order orderToSell, double initialBtcSum) {
        double maxToBuy = Collections.min(
                Arrays.asList(orderToSell.getAmount(),
                        orderToBuy.getAmount(),
                        cheapest.getWalletAmount(coin),
                        expensive.getWalletAmount(coin)));
        double maxToSell = maxToBuy * (1 - cheapest.getTakerFee());

        double spentBtc = maxToBuy * orderToBuy.getPrice();
        double receivedCoin = cheapest.buyCoinWithFee(spentBtc, coin);

        double receivedBtc = expensive.sellCoinWithFee(maxToSell, coin);

        Log.debug("Max: " + maxToBuy + " - IOTA: " + receivedCoin + " - BTC:" + receivedBtc);
        cheapest.setWallet(coin, cheapest.getWalletAmount(coin) + receivedCoin);
        cheapest.setWallet(BTC, cheapest.getWalletAmount(BTC) - spentBtc);
        expensive.setWallet(coin, expensive.getWalletAmount(coin) - maxToSell);
        expensive.setWallet(BTC, expensive.getWalletAmount(BTC) + receivedBtc);
        Log.debug(cheapest + " - " + coin.name() + ": " + cheapest.getWalletAmount(coin) + " - BTC: " + cheapest.getWalletAmount(BTC));
        Log.debug(expensive + " - " + coin.name() + ": " + expensive.getWalletAmount(coin) + " - BTC: " + expensive.getWalletAmount(BTC));
        Log.debug("Sums: " + coin.name() + ": " + Exchange.getSumInWallets(coin, cheapest, expensive));
        double finalBtc = Exchange.getSumInWallets(BTC, cheapest, expensive);
        Log.debug("Sums: " + BTC.name() + ": " + finalBtc);
        double diff = finalBtc - initialBtcSum;
        Log.debug("Btc increase: " + diff + " - Diff percent: " + ((finalBtc / initialBtcSum - 1) * 100) + "%");
        return maxToBuy;
    }

    public void start() {
        binanceConnector.start();
        bitfinexConnector.start();
        @SuppressWarnings("unchecked") Flowable<Exchange> binanceFlowable = binanceConnector.asFlowable();
        @SuppressWarnings("unchecked") Flowable<Exchange> bitfinexFlowable = bitfinexConnector.asFlowable();

        Flowable.combineLatest(binanceFlowable, bitfinexFlowable, (binance, bitfinex) -> {
            Log.debug("Will check");
            simulateOperation(IOTA, binance, bitfinex);
            return "DONE";
        })
//                .subscribeOn(Schedulers.computation())
                .subscribe(new DisposableSubscriber<Object>() {
                    @Override
                    public void onNext(Object o) {
                        Log.print(o.toString());
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.print("FINISHED PROCESSING");
                    }
                });
    }
}
