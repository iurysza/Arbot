package javao;

import org.junit.Before;
import org.junit.Test;

import src.Bitfinex;

import static org.junit.Assert.assertTrue;
import static src.base.Coin.LTC;

public class BitfinexTest {

    private final Bitfinex bitfinex = new Bitfinex();

    @Before
    public void setUp() throws Exception {
//        bitfinex.putOrderBook(IOTA, createFakeOrderBook());
        bitfinex.putOrderBook(LTC, OrderBooks.createFakeOrderBookLTCBitfinex());
    }

    @Test
    public void buyCoinWithFee() throws Exception {
        double ltc = bitfinex.buyCoinWithFee(0.1675541798469, LTC);
        double fee = 0.01011251 * 2;
        double finalLTC = 10.11251010 - fee;
        System.out.println(ltc + " - " + finalLTC);
        assertTrue(Math.abs(ltc - finalLTC) < 0.000001);
    }

    @Test
    public void sellCoinWithFee() throws Exception {
        System.out.println(10.54739759 * 0.01669);
        double btc = bitfinex.sellCoinWithFee(10.54739759, LTC);
        double fee = 0.00017604 * 2;
        double finalBTC = 0.176036065777 - fee;
        System.out.println(btc + " - " + finalBTC);
        assertTrue(Math.abs(btc - finalBTC) < 0.000001);
    }
}