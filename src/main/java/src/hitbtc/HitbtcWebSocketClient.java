package src.hitbtc;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import src.Log;
import src.Utils;
import src.base.Coin;
import src.base.Order;
import src.base.OrderBook;
import src.binance.data.ExchangeConnector;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class HitbtcWebSocketClient extends WebSocketClient {
    private HitbtcConfig config;
    private Hitbtc hitbtc;
    private Coin coin;
    private ExchangeConnector.ExchangeResult exchangeResult;

    public HitbtcWebSocketClient(Hitbtc hitbtc, Coin coin, ExchangeConnector.ExchangeResult exchangeResult) throws URISyntaxException {
        super(new URI(HitbtcConfig.uri));
        config = HitbtcConfig.getOrderBookConfig(coin);
        this.hitbtc = hitbtc;
        this.coin = coin;
        this.exchangeResult = exchangeResult;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.print("Successfully connected with "+hitbtc.getName()+"...");
        this.subscribeToOrderBook();
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.print("Disconnected from "+hitbtc.getName()+"...");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onMessage(String message) {
        HitbtcResponse response = Utils.gson.fromJson(message, HitbtcResponse.class);
        if(response == null || response.method == null)
            return;

        if (response.method.equals("snapshotOrderbook")) {
            getSnapshot(response);
        } else if (response.method.equals("updateOrderbook")) {
            getUpdate(response);
        }
    }

    private void subscribeToOrderBook() {
        this.send(config.generateJSON());
    }

    private void getSnapshot(HitbtcResponse response) {
        OrderBook orderBook = new OrderBook();
        orderBook.bids.addAll(response.params.bid);
        orderBook.asks.addAll(response.params.ask);

        hitbtc.putOrderBook(coin, orderBook);
    }

    private void getUpdate(HitbtcResponse response){
        OrderBook orderBook = hitbtc.getOrderBook(coin);
        List<Order> asks = orderBook.asks;
        List<Order> bids = orderBook.bids;

        for(Order ask : response.params.ask){

            int index = 0;
            while(index < asks.size() && ask.getPrice() > asks.get(index).getPrice()) index++;

            updateOrders(asks, ask, index);
        }

        for(Order bid : response.params.bid){
            int index = 0;
            while(index < bids.size() && bid.getPrice() < bids.get(index).getPrice()) index++;

            updateOrders(bids, bid, index);
        }

        exchangeResult.onResult(hitbtc);
//        Log.print(" ------------------------ "+hitbtc.getName()+" --------------------- ");
//        Log.print(orderBook.toString());
    }

    private void updateOrders(List<Order> orders, Order order, int index) {
        if(order.getAmount() <= 0) {
            orders.remove(index);
        } else if (index < orders.size() && order.getPrice().equals(orders.get(index).getPrice())){
            orders.get(index).setAmount(order.getAmount());
        } else{
            orders.add(index, order);
        }
    }
}
