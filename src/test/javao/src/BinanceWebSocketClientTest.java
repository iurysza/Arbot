package src;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.binance.BinanceConfig;
import src.binance.BinanceWebSocketClient;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static src.base.Coin.BTC;
import static src.base.Coin.IOTA;

public class BinanceWebSocketClientTest {

    private BinanceWebSocketClient binanceWebSocketClient;

    @Before
    public void setup() {
        BinanceConfig binanceConfig = new BinanceConfig(IOTA, BTC);
    }

    @Test
    public void test() {
        Assert.assertNotNull(binanceWebSocketClient);
        binanceWebSocketClient.connect();
        verify(binanceWebSocketClient, times(1)).run();
    }

}