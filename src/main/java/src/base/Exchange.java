package src.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Exchange {

    private ConcurrentHashMap<Coin, Double> transferFees = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Coin, OrderBook> orderBooks = new ConcurrentHashMap<>();

    public Exchange() {

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

    protected double buyCoin(double amount, Coin coin) {
        OrderBook orderBook = orderBooks.get(coin);
        double bought = 0;
        double amountAvailable;
        for (Order ask : orderBook.asks) {
            amountAvailable = ask.getAmount();
            if (amount < ask.getAmount()) {
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

    protected double sellCoin(double amount, Coin coin) {
        OrderBook orderBook = orderBooks.get(coin);
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
     * @return The amount of new coins after buying.
     */
    public double buyCoinWithFee(double coinAmount, Coin coin) {
        double alt = buyCoin(coinAmount, coin);
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
        double alt = sellCoin(coinAmount, coin);
        alt -= alt * getTakerFee();
        return alt;
    }
}
