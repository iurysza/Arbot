package src;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import src.base.Coin;
import src.base.Exchange;
import src.binance.BinanceConnector;
import src.bitfinex.Bitfinex;
import src.binance.Binance;
import src.binance.BinanceConfig;
import src.bitfinex.BitfinexConnector;

import static src.base.Coin.BTC;
import static src.base.Coin.IOTA;
import static src.base.Coin.LTC;

public class ProcessorTest {

    private Processor processor;
    private BinanceConnector binanceConnector;
    private BitfinexConnector bitfinexConnector;

    @Before
    public void setUp() throws Exception {
        binanceConnector = new BinanceConnector();
        bitfinexConnector = new BitfinexConnector();
        processor = new Processor(binanceConnector, bitfinexConnector);
    }

    @Test
    public void checkOrderBooksProfit() throws Exception {
        Coin coin = IOTA;
        Binance binance = new Binance(new BinanceConfig(coin, BTC));
        binance.putOrderBook(coin, OrderBooks.createFakeOrderBook());
        binance.setWallet(IOTA, 300);
        binance.setWallet(BTC, 0.01);
        Bitfinex bitfinex = new Bitfinex();
        bitfinex.putOrderBook(coin, OrderBooks.createFakeOrderBookLowerPrice());
        bitfinex.setWallet(IOTA, 200);
        bitfinex.setWallet(BTC, 0.01);
        Exchange cheapest = processor.simulateOperation(coin, binance, bitfinex);
        Assert.assertTrue(cheapest == bitfinex);
    }

    @Test
    public void start() throws Exception {
        Binance binance = new Binance(new BinanceConfig(LTC, BTC));
        Bitfinex bitfinex = new Bitfinex();
        binance.putOrderBook(IOTA, OrderBooks.createFakeOrderBook());
        bitfinex.putOrderBook(IOTA, OrderBooks.createFakeOrderBookLowerPrice());
        binanceConnector.test(binance);
        bitfinexConnector.test(bitfinex);
        processor.start();
    }

}