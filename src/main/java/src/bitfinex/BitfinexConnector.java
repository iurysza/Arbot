package src.bitfinex;


import src.base.Coin;
import src.base.Exchange;
import src.data.ExchangeConnector;

import java.net.URISyntaxException;

public class BitfinexConnector extends ExchangeConnector<Bitfinex> implements ExchangeConnector.ExchangeResult {

    private final Bitfinex bitfinex;
    private final BitfinexWebSocketClient socketClient;

    public BitfinexConnector() throws URISyntaxException {
        bitfinex = new Bitfinex();
        socketClient = new BitfinexWebSocketClient(bitfinex, Coin.IOTA, this);
    }

    @Override
    public void start() {
        socketClient.connect();
    }

    @Override
    public void onResult(Exchange exchange) {
        this.onExchangeUpdated((Bitfinex) exchange);
    }
}
