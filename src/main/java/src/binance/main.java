package src.binance;

import static src.base.Coin.BTC;
import static src.base.Coin.IOTA;

public class main {

    public static void main(String[] args) {
        BinanceConfig binanceConfig = new BinanceConfig(IOTA, BTC);
//        URI defaultBinanceOrderBook = binanceConfig.getOrdebookSnapshot();
//        BinanceWebSocketClient binanceWebSocketClient = new BinanceWebSocketClient(defaultBinanceOrderBook);
//        binanceWebSocketClient.connect();
    }
}
