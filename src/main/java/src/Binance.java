package src;

import java.net.URI;
import java.util.Map;

import src.base.Coin;
import src.base.Exchange;
import src.base.OrderBook;


public class Binance extends Exchange {

    BinanceWebSocketClient webSocketClient;


    public static final String BINANCE_ORDER_BOOK_URL = "wss://stream.binance.com:9443/ws/btcusd@depth";
//    wss://stream.binance.com:9443/ws/[symbol in lower case]@depth   (e.g. wss://stream.binance.com:9443/ws/ethbtc@depth)

    public Binance() {
        webSocketClient = new BinanceWebSocketClient(URI.create(BINANCE_ORDER_BOOK_URL));
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
}
