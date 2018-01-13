package javao;

import org.junit.Before;
import org.junit.Test;

import src.base.Coin;
import src.binance.Binance;
import src.binance.BinanceConfig;

public class BinanceExchangeTest {

    @Before
    public void setUp() throws Exception {
        Binance binance = new Binance(new BinanceConfig(Coin.BTC, Coin.LTC));

    }

    @Test
    public void isWebSocketWorking() throws Exception {

    }
}