package src.binance;

import java.net.URI;

import lombok.Data;
import src.base.Coin;

@Data
public class BinanceConfig {

    public static final String BINANCE_ORDER_BOOK_URL = "wss://stream.binance.com:9443/ws/btcusd@depth";
    private final Coin fromCoin;
    private final Coin toCoin;

    public BinanceConfig(Coin fromCoin, Coin toCoin) {
        this.fromCoin = fromCoin;
        this.toCoin = toCoin;
    }

    public URI getDefaultBinanceOrderBook() {
        String url = "wss://stream.binance.com:9443/ws/" +
                fromCoin +
                toCoin +
                "@depth";

        return URI.create(url);
    }


}
