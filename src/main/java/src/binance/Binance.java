package src.binance;

import java.net.URI;
import java.util.Map;

import src.base.Coin;
import src.base.Exchange;
import src.base.OrderBook;

import static src.base.Coin.BTC;
import static src.base.Coin.LTC;


public class Binance extends Exchange {

    BinanceWebSocketClient webSocketClient;

    public Binance(BinanceConfig binanceConfig) {
        webSocketClient = new BinanceWebSocketClient(binanceConfig.getDefaultBinanceOrderBook());
    }


    @Override
    public String getName() {
        return "Binance";
    }

    @Override
    public double getMakerFee() {
        return 0.001;
    }

    @Override
    public double getTakerFee() {
        return 0.001;
    }


    @Override
    protected void fillTransferFees(Map<Coin, Double> transferFees) {
        transferFees.put(Coin.BTC, 0.001);
        transferFees.put(Coin.LTC, 0.01);
        transferFees.put(Coin.IOTA, 0.05);
    }

    @Override
    public OrderBook getOrderBook(Coin coin) {
        return super.getOrderBook(coin);
    }

    public static void main(String[] args) {
        BinanceConfig binanceConfig = new BinanceConfig(LTC, BTC);
        URI defaultBinanceOrderBook = binanceConfig.getDefaultBinanceOrderBook();
        BinanceWebSocketClient binanceWebSocketClient = new BinanceWebSocketClient(defaultBinanceOrderBook);
    }
}
