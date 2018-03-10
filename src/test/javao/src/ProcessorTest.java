package src;

import org.junit.Before;
import org.junit.Test;
import src.base.Coin;
import src.base.OrderBook;
import src.binance.Binance;
import src.binance.BinanceConnector;
import src.bitfinex.Bitfinex;
import src.bitfinex.BitfinexConnector;
import src.hitbtc.Hitbtc;
import src.hitbtc.HitbtcConnector;

import java.util.Arrays;

import static src.base.Coin.*;

public class ProcessorTest {

    private Processor processor;
    private BinanceConnector binanceConnector;
    private BitfinexConnector bitfinexConnector;
    private HitbtcConnector hitbtcConnector;

    @Before
    public void setUp() throws Exception {
        binanceConnector = BinanceConnector.createDefaultConnector(TRX);
        bitfinexConnector = new BitfinexConnector(TRX);
        hitbtcConnector = new HitbtcConnector(TRX);
        processor = new Processor(TRX, bitfinexConnector, hitbtcConnector);
    }

    @Test
    public void checkOrderBooksProfit() throws Exception {
        Coin coin = IOTA;
        Hitbtc hitbtc = new Hitbtc();
        hitbtc.putOrderBook(coin, OrderBooks.createFakeOrderBook());
        hitbtc.setWallet(IOTA, 3000);
        hitbtc.setWallet(TRX, 20000);
        hitbtc.setWallet(BTC, 0.6);
        Bitfinex bitfinex = new Bitfinex();
        bitfinex.putOrderBook(coin, OrderBooks.createFakeOrderBookLowerPrice());
        bitfinex.setWallet(IOTA, 2000);
        bitfinex.setWallet(TRX, 20000);
        bitfinex.setWallet(BTC, 0.5);
        OrderBook orderBook = processor.simulateOperation(coin, Arrays.asList(hitbtc, bitfinex));
        System.out.println(orderBook);
//        Assert.assertTrue(cheapest == bitfinex);
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