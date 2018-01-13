package javao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.URI;

import src.binance.BinanceConfig;
import src.binance.BinanceWebSocketClient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static src.base.Coin.BTC;
import static src.base.Coin.LTC;

public class BinanceWebSocketClientTest {

    private BinanceWebSocketClient binanceWebSocketClient;

    @Before
    public void setup() {
        BinanceConfig binanceConfig = new BinanceConfig(LTC, BTC);
        URI defaultBinanceOrderBook = binanceConfig.getDefaultBinanceOrderBook();
        binanceWebSocketClient = new BinanceWebSocketClient(defaultBinanceOrderBook);
        binanceWebSocketClient=   Mockito.spy(new BinanceWebSocketClient(defaultBinanceOrderBook));
    }

    @Test
    public void test() {
        Assert.assertNotNull(binanceWebSocketClient);
        BinanceWebSocketClient client = mock(BinanceWebSocketClient.class);

        verify(client, times(1)).onOpen(any());
    }

}