package src.bitfinex;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import src.Log;
import src.Utils;
import src.base.Coin;
import src.base.Order;
import src.base.OrderBook;
import src.ExchangeConnector;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class BitfinexWebSocketClient extends WebSocketClient {

    private BitfinexConfig config;
    private boolean snapshot;
    private boolean connected;
    private boolean subscribed;
    private Bitfinex bitfinex;
    private Coin coin;
    private ExchangeConnector.ExchangeResult exchangeResult;

    BitfinexWebSocketClient(Bitfinex bitfinex, Coin coin, ExchangeConnector.ExchangeResult exchangeResult) throws Exception {
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
            Order order = new Order(s.get(0), Math.abs(s.get(2)));
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

        Double price = list.get(1);
        Double count = list.get(2);
        Double amount = list.get(3);
        OrderBook orderBook = bitfinex.getOrderBook(coin);

        List<Order> orders;
        int index = 0;
        if (amount > 0) { //bid
            orders = orderBook.bids;
            while(index < orders.size() && price < orders.get(index).getPrice()) index++;
        } else { //ask
            orders = orderBook.asks;
            while(index < orders.size() && price > orders.get(index).getPrice()) index++;
        }

        if(count > 0){ //add or update
            if(index == orders.size()){
                orders.add(new Order(price, Math.abs(amount)));
            } else if (price.equals(orders.get(index).getPrice())){
                orders.set(index, new Order(orders.get(index).getPrice(), Math.abs(amount)));
            } else {
                orders.add(index, new Order(price, Math.abs(amount)));
            }
        } else { // remove
            if(index < orders.size()){
                orders.remove(index);
            }
        }

        exchangeResult.onResult(bitfinex);
//        Log.print(" ------------------------ "+bitfinex.getName()+" --------------------- ");
//        Log.print(orderBook.toString());
    }
}
