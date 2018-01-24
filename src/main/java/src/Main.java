package src;

import java.net.URISyntaxException;

import src.binance.BinanceConnector;
import src.bitfinex.BitfinexConnector;

public class Main {
    public static void main(String... args) throws URISyntaxException {
        Processor processor = new Processor(new BinanceConnector(), new BitfinexConnector());
        processor.start();
    }
}
