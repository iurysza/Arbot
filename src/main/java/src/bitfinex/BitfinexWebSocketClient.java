package src.bitfinex;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import src.Log;

import java.net.URI;
import java.net.URISyntaxException;

public class BitfinexWebSocketClient extends WebSocketClient {

    private BitfinexConfig config;

    public BitfinexWebSocketClient() throws URISyntaxException {
        super(new URI(BitfinexConfig.uri));

        this.config = BitfinexConfig.getDefaultConfig();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.print("Successfully connected with Bitfinex...");
        this.subscribeToOrderBook();
    }

    @Override
    public void onMessage(String message) {
        Log.print(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.print("Disconnected from Bitfinex...");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public void subscribeToOrderBook() {
        String json = config.generateJSON();
        this.send(json);
    }
}
