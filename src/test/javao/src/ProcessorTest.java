package src;

import org.junit.Before;
import org.junit.Test;

import src.bitfinex.Bitfinex;
import src.Processor;
import src.binance.Binance;
import src.binance.BinanceConfig;
import src.bitfinex.BitfinexConnector;
import src.data.BinanceConnector;

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