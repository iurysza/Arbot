package src;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.base.Coin;
import src.base.Exchange;
import src.binance.Binance;
import src.binance.BinanceConnector;
import src.bitfinex.Bitfinex;
import src.bitfinex.BitfinexConnector;

import static src.base.Coin.BTC;
import static src.base.Coin.IOTA;

public class ProcessorTest {

    private Processor processor;
    private BinanceConnector binanceConnector;
    private BitfinexConnector bitfinexConnector;

    @Before
    public void setUp() throws Exception {
        binanceConnector = BinanceConnector.createDefaultConnector(IOTA);
        bitfinexConnector = new BitfinexConnector(IOTA);
        processor = new Processor(binanceConnector, bitfinexConnector);
    }

    @Test
    public void checkOrderBooksProfit() throws Exception {
        Coin coin = IOTA;
        Binance binance = new Binance();
        binance.putOrderBook(coin, OrderBooks.createFakeOrderBook());
        binance.setWallet(IOTA, 3000);
        binance.setWallet(BTC, 0.6);
        Bitfinex bitfinex = new Bitfinex();
        bitfinex.putOrderBook(coin, OrderBooks.createFakeOrderBookLowerPrice());
        bitfinex.setWallet(IOTA, 2000);
        bitfinex.setWallet(BTC, 0.5);
        Exchange cheapest = processor.simulateOperation(coin, binance, bitfinex);
        Assert.assertTrue(cheapest == bitfinex);
    }

    @Test
    public void start() throws Exception {
        Binance binance = new Binance();
        Bitfinex bitfinex = new Bitfinex();
        binance.putOrderBook(IOTA, OrderBooks.createFakeOrderBook());
        bitfinex.putOrderBook(IOTA, OrderBooks.createFakeOrderBookLowerPrice());
        binanceConnector.test(binance);
        bitfinexConnector.test(bitfinex);
        processor.start();
    }

}