package src;

import src.base.Coin;
import src.bitfinex.BitfinexConnector;
import src.hitbtc.HitbtcConnector;

import java.net.URISyntaxException;

import static src.base.Coin.TRX;

public class Main {
    public static void main(String... args) throws URISyntaxException {
        Coin coin = TRX;
        Processor processor = new Processor(coin, new HitbtcConnector(coin), new BitfinexConnector(coin));
        processor.start();
    }
}
