package src;

import java.io.IOException;
import java.net.URISyntaxException;

import src.base.Coin;
import src.bitfinex.BitfinexConnector;
import src.hitbtc.HitbtcConnector;

public class Main {
    public static void main(String... args) throws URISyntaxException, IOException {
        Coin coin = Coin.TRX;
        Processor processor = new Processor(coin, new HitbtcConnector(coin), new BitfinexConnector(coin));
        processor.start();
    }
}
