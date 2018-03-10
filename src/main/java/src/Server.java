package src;


import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server extends WebSocketServer {

    public Server(int port) throws IOException {
        super(new InetSocketAddress("0.0.0.0", port));
    }

    @Override
    public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket conn, Draft draft, ClientHandshake request) throws InvalidDataException {
        System.out.println("MSG");
        ServerHandshakeBuilder builder = super.onWebsocketHandshakeReceivedAsServer(conn, draft, request);
        builder.put("Access-Control-Allow-Origin", "*");
        System.out.println(builder.getHttpStatusMessage());
        builder.iterateHttpFields().forEachRemaining(System.out::println);
        return builder;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("OPEN");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("CLOSE");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Message: " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("ERROR: " + ex);

    }

    @Override
    public void onStart() {
        System.out.println("START");
    }

}