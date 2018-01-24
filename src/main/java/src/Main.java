package src;

import src.base.Coin;
import src.binance.BinanceConnector;
import src.bitfinex.BitfinexConnector;

import java.net.URISyntaxException;

public class Main {
    public static void main(String... args) throws URISyntaxException {
        Processor processor = new Processor(BinanceConnector.createDefaultConnector(Coin.IOTA), new BitfinexConnector(Coin.IOTA));
        processor.start();
    }
}
