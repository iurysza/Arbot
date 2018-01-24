package src;

import java.net.URISyntaxException;

import src.base.Coin;
import src.binance.BinanceConnector;
import src.bitfinex.BitfinexConnector;

public class Main {
    public static void main(String... args) throws URISyntaxException {
        Processor processor = new Processor(BinanceConnector.createDefaultConnector(Coin.LTC), new BitfinexConnector());
        processor.start();
    }
}
