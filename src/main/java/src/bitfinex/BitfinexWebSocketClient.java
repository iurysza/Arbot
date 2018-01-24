package src.bitfinex;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import src.Log;
import src.Utils;
import src.base.Coin;
import src.base.Order;
import src.base.OrderBook;
import src.binance.data.ExchangeConnector;

public class BitfinexWebSocketClient extends WebSocketClient {

    private BitfinexConfig config;
    private boolean snapshot;
    private boolean connected;
    private boolean subscribed;
    private Bitfinex bitfinex;
    private Coin coin;
    private ExchangeConnector.ExchangeResult exchangeResult;

    public BitfinexWebSocketClient(Bitfinex bitfinex, Coin coin, ExchangeConnector.ExchangeResult exchangeResult) throws URISyntaxException {
        super(new URI(BitfinexConfig.uri));
        this.bitfinex = bitfinex;
        this.coin = coin;
        this.exchangeResult = exchangeResult;

        this.config = BitfinexConfig.getConfigByCoin(this.coin);
        this.snapshot = false;
        this.connected = false;
        this.subscribed = false;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.print("Successfully connected with Bitfinex...");
        this.subscribeToOrderBook();
    }

    @Override
    public void onMessage(String message) {
        if (!connected) {
            Log.print(message);
            connected = true;

            return;
        }

        if (!subscribed) {
            Log.print(message);
            subscribed = true;
            snapshot = true;

            return;
        }


        if (snapshot) {
            snapshot = false;
            getSnapshot(message);
        } else {
            getUpdate(message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.print("Disconnected from Bitfinex...");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    private void subscribeToOrderBook() {
        String json = config.generateJSON();
        this.send(json);
    }

    private void getSnapshot(String message) {
        @SuppressWarnings("unchecked") // ["<CHANNEL_ID>", [["<PRICE>", "<COUNT>", "<AMOUNT>"],...]]
                ArrayList list = Utils.gson.fromJson(message, ArrayList.class);
        @SuppressWarnings("unchecked")
        ArrayList<ArrayList<Double>> snapshot = (ArrayList<ArrayList<Double>>) list.get(1);

        OrderBook orderBook = new OrderBook();
        for (ArrayList<Double> s : snapshot) {
            Order order = new Order(s.get(1), s.get(2));
            if (s.get(2) > 0) { //bid
                orderBook.bids.add(order);
            } else { //ask
                orderBook.asks.add(order);
            }
        }
        bitfinex.putOrderBook(coin, orderBook);
    }

    private void getUpdate(String message) {
        if (message.contains("hb")) {
            return;
        }

        @SuppressWarnings("unchecked") // ["<CHANNEL_ID>", "<PRICE>", "<COUNT>", "<AMOUNT>"]
                ArrayList<Double> list = (ArrayList<Double>) Utils.gson.fromJson(message, ArrayList.class);

        OrderBook orderBook = bitfinex.getOrderBook(coin);
        List<Order> orders;
        boolean isAsk = false;
        if (list.get(3) > 0) { //bid
            orders = orderBook.bids;
        } else { //ask
            orders = orderBook.asks;
            isAsk = true;
        }

        boolean has = false;
        for (Order order : orders) {
            if (order.getPrice().equals(list.get(1))) {
                order.setAmount(list.get(3));
                has = true;
                break;
            }
        }
        if (!has) {
            orders.add(new Order(list.get(1), list.get(3)));
            if (isAsk) {
                orderBook.sortAsks();
            } else {
                orderBook.sortBids();
            }
        }

        exchangeResult.onResult(bitfinex);
    }
}
