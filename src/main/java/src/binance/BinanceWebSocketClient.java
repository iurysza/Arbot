package src.binance;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import src.base.Order;
import src.base.OrderBook;
import src.binance.data.ExchangeConnector;


/**
 * This example demonstrates how to create a websocket connection to a server. Only the most important callbacks are overloaded.
 */
public class BinanceWebSocketClient extends WebSocketClient {


    private Binance binance;
    private BinanceConfig config;
    private ExchangeConnector.ExchangeResult exchangeResult;
    private Gson gson;

    public BinanceWebSocketClient(URI serverUri, Draft draft) {
        super(serverUri, draft);

    }

    public BinanceWebSocketClient(@Nonnull Binance binance, BinanceConfig config) {
        super(config.getOrdebookUpdate());
        this.binance = binance;
        this.config = config;
        gson = new Gson();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("opened connection");

        // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
    }

    @Override
    public void onMessage(String message) {
        System.out.println("received: " + message);

        Depth depth = gson.fromJson(message, new TypeToken<Depth>() {}.getType());

        List<Order> askList = getOrders(depth.getAsks());
        List<Order> bidList = getOrders(depth.getBids());

        OrderBook orderBook = binance.getOrderBook(config.getFromCoin());

        exchangeResult.onResult(binance);

    }

    @NotNull
    private List<Order> getOrders(List<List<String>> items) {
        List<Order> orderList = new ArrayList<>();
        for (List<String> ask : items) {
            orderList.add(new Order(Double.valueOf(ask.get(0)), Double.valueOf(ask.get(1))));
        }
        return orderList;
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // The codecodes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println("Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
    }


    public void setExchangeResult(BinanceConnector exchangeResult) {
        this.exchangeResult = exchangeResult;
    }

    public void setConfig(BinanceConfig config) {
        this.config = config;
    }
}
