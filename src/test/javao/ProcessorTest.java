package javao;

import org.junit.Before;
import org.junit.Test;

import src.Binance;
import src.Bitfinex;
import src.Processor;
import src.data.BinanceConnector;
import src.data.BitfinexConnector;

import static src.base.Coin.IOTA;

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