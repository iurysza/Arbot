package src.bitfinex;


import java.net.URISyntaxException;

import src.base.Coin;
import src.base.Exchange;
import src.binance.data.ExchangeConnector;

public class BitfinexConnector extends ExchangeConnector<Bitfinex> implements ExchangeConnector.ExchangeResult {

    private Bitfinex bitfinex;
    private BitfinexWebSocketClient socketClient;

    public BitfinexConnector(Coin coin) {
        try {
            bitfinex = new Bitfinex();
            socketClient = new BitfinexWebSocketClient(bitfinex, coin, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
