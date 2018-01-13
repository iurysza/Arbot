package src.bitfinex;


import src.data.ExchangeConnector;

import java.net.URISyntaxException;

public class BitfinexConnector extends ExchangeConnector<Bitfinex> {

    private final Bitfinex bitfinex;
    private final BitfinexWebSocketClient socketClient;

    public BitfinexConnector() throws URISyntaxException {
        bitfinex = new Bitfinex();
        socketClient = new BitfinexWebSocketClient();
    }

    @Override
    public void start() {
        socketClient.connect();
    }
}
