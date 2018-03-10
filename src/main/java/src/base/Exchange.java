package src.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Exchange {

    private ConcurrentHashMap<Coin, Double> transferFees = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Coin, OrderBook> orderBooks = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Coin, Double> wallets = new ConcurrentHashMap<>();
    private String name;
    private Double makerFee;
    private Double takerFee;

    public Exchange() {
        name = getName();
        makerFee = getMakerFee();
        takerFee = getTakerFee();
    }

    public abstract String getName();

    /***
     * Get the transfer fee for a specific coin.
     * @param coin coin to get the transfer fee.
     * @return The transfer fee of the given coin.
     */
    public double getTransferFee(Coin coin) {
        return transferFees.get(coin);
    }

    /***
     * Inserts an order book for this given coin with BTC.
     * @param coin Altcoin to insert the order book into.
     */
    public void putOrderBook(Coin coin, OrderBook orderBook) {
        orderBooks.put(coin, orderBook);
    }

    /***
     * Gets the available order book for this given coin with BTC.
     * @param coin Altcoin to retrieve the order book.
     * @return The order book of that altcoin.
     */
    public OrderBook getOrderBook(Coin coin) {
        return orderBooks.get(coin);
    }

    protected double buyCoin(double amount, Coin coin, OrderBook orderBook) {
//        OrderBook orderBook = orderBooks.get(coin);
        double bought = 0;
        double amountAvailable;
        for (Order ask : orderBook.asks) {
            amountAvailable = ask.getBtcAmount();
            if (amount < amountAvailable) {
                amountAvailable = amount;
            }
            if (amountAvailable <= 0) {
                break;
            }
            double boughtNow = amountAvailable / ask.getPrice();
            bought += boughtNow;
            amount -= amountAvailable;
//            Log.debug("Bought " + boughtNow + " at " + ask.getPrice() + ". Total bought: " + bought + " - Left: " + amount);
        }
        return bought;
    }

    protected double sellCoin(double amount, Coin coin, OrderBook orderBook) {
//        OrderBook orderBook = orderBooks.get(coin);
        double sold = 0;
        double amountAvailable;
        for (Order bid : orderBook.bids) {
            amountAvailable = bid.getAmount();
            if (amount < bid.getAmount()) {
                amountAvailable = amount;
            }
            if (amountAvailable <= 0) {
                break;
            }

            sold += bid.getPrice() * amountAvailable;
            amount -= amountAvailable;
//            Log.debug("Sold " + amountAvailable + " at " + bid.getPrice() + ". Total sold by: " + sold + " - Left: " + amount);
        }
        return sold;
    }

    /***
     * Get the fee for exchanging as a maker.
     * @return The maker fee.
     */
    public abstract double getMakerFee();

    /***
     * Get the fee for exchanging as a taker.
     * @return The taker fee.
     */
    public abstract double getTakerFee();

    /***
     * Get the transfer fee for a specific coin.
     * @param transferFees Transfer fees map.
     */
    protected abstract void fillTransferFees(Map<Coin, Double> transferFees);

    /***
     * Calculates buying the coinAmount in the given orderBook.
     * @param coinAmount amount of coins to buy.
     * @param coin Coin which will be bought.
     * @param orderBook Order book in which the buying will happen
     * @return The amount of new coins after buying.
     */
    public double buyCoinWithFee(double coinAmount, Coin coin, OrderBook orderBook) {
        double alt = buyCoin(coinAmount, coin, orderBook);
        alt -= alt * getTakerFee();
        return alt;
    }

    /***
     * Calculates buying the coinAmount in the given orderBook.
     * @param coinAmount amount of coins to buy.
     * @param coin Coin which will be bought.
     * @return The amount of new coins after buying.
     */
    public double buyCoinWithFee(double coinAmount, Coin coin) {
        OrderBook orderBook = orderBooks.get(coin);
        return buyCoinWithFee(coinAmount, coin, orderBook);
    }

    /***
     * Calculates selling the coinAmount in the given orderBook.
     * @param coinAmount amount of coins to sell.
     * @param coin Coin which will be sold.
     * @param orderBook Order book in which the selling will happen
     * @return The amount of new coins after selling.
     */
    public double sellCoinWithFee(double coinAmount, Coin coin, OrderBook orderBook) {
        double alt = sellCoin(coinAmount, coin, orderBook);
        alt -= alt * getTakerFee();
        return alt;
    }

    /***
     * Calculates selling the coinAmount in the given orderBook.
     * @param coinAmount amount of coins to sell.
     * @param coin Coin which will be sold.
     * @return The amount of new coins after selling.
     */
    public double sellCoinWithFee(double coinAmount, Coin coin) {
        OrderBook orderBook = orderBooks.get(coin);
        return sellCoinWithFee(coinAmount, coin, orderBook);
    }

    /***
     * Sets a given value of coins to the wallet
     * @param coin Coin to set the wallet.
     * @param coinAmount amount of coins to set in the wallet.
     */
    public void setWallet(Coin coin, double coinAmount) {
        wallets.put(coin, coinAmount);
    }

    @Override
    public String toString() {
        return getName();
    }

    /***
     * Returns the amount of coins in the wallet.
     * @param coin Coin to get the wallet.
     * @return The amount of coins in the wallet.
     */
    public double getWalletAmount(Coin coin) {
        return wallets.get(coin);
    }

    public static double getSumInWallets(Coin coin, Exchange... exchanges) {
        double sum = 0;
        for (Exchange exchange : exchanges) {
            sum += exchange.getWalletAmount(coin);
        }
        return sum;
    }
}
