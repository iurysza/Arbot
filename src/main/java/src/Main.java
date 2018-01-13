package src;

import src.data.BinanceConnector;
import src.data.BitfinexConnector;

public class Main {
    public static void main(String... args) {
        Processor processor = new Processor(new BinanceConnector(), new BitfinexConnector());
        processor.start();
    }
}
