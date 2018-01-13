package src;

import src.base.Exchange;
import src.base.OrderBook;
import src.data.BinanceConnector;
import src.bitfinex.BitfinexConnector;
import src.data.ExchangeConnector;
import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;

import static src.base.Coin.IOTA;

public class Processor {

    private ExchangeConnector binanceConnector;
    private ExchangeConnector bitfinexConnector;

    public Processor(BinanceConnector binanceConnector, BitfinexConnector bitfinexConnector) {
        this.binanceConnector = binanceConnector;
        this.bitfinexConnector = bitfinexConnector;
    }

    public void start() {
        binanceConnector.start();
        bitfinexConnector.start();
        @SuppressWarnings("unchecked") Flowable<Exchange> binanceFlowable = binanceConnector.asFlowable();
        @SuppressWarnings("unchecked") Flowable<Exchange> bitfinexFlowable = bitfinexConnector.asFlowable();

        Flowable.combineLatest(binanceFlowable, bitfinexFlowable, (binance, bitfinex) -> {
            OrderBook binanceOrderBook = binance.getOrderBook(IOTA);
            OrderBook bitfinexOrderBook = bitfinex.getOrderBook(IOTA);
            Log.debug("Will check");
            if (binanceOrderBook.asks.get(0).getPrice() < bitfinexOrderBook.bids.get(0).getPrice()) {
                Log.debug("Buy at Binance and sell at Bitfinex");
            } else if (bitfinexOrderBook.asks.get(0).getPrice() < binanceOrderBook.bids.get(0).getPrice()) {
                Log.debug("Buy at Bitfinex and sell at Binance");
            }
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