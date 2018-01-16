package src;

import org.junit.Before;
import org.junit.Test;

import src.binance.Binance;
import src.binance.BinanceConfig;

import static org.junit.Assert.assertTrue;
import static src.base.Coin.BTC;
import static src.base.Coin.LTC;

public class BinanceTest {

    private final Binance binance = new Binance(new BinanceConfig(LTC,BTC));

    @Before
    public void setUp() throws Exception {
//        binance.putOrderBook(IOTA, createFakeOrderBook());
        binance.putOrderBook(LTC, OrderBooks.createFakeOrderBookLTCBinance());
    }

    @Test
    public void buyCoinWithFee() throws Exception {
        double ltc = binance.buyCoinWithFee(0.29769633, LTC);
        double finalLTC = 17.87 - 0.01787000;
        System.out.println(ltc + " - " + finalLTC);
        assertTrue(Math.abs(ltc - finalLTC) < 0.000001);
    }

    @Test
    public void sellCoinWithFee() throws Exception {
        double btc = binance.sellCoinWithFee(17.85, LTC);
        double finalBTC = 0.29863050 - 0.00029863;
        System.out.println(btc + " - " + finalBTC);
        assertTrue(Math.abs(btc - finalBTC) < 0.000001);
    }
}