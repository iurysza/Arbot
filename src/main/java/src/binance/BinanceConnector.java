package src.binance;

import java.net.URISyntaxException;

import src.base.Exchange;
import src.binance.data.ExchangeConnector;

public class BinanceConnector extends ExchangeConnector<Binance> implements ExchangeConnector.ExchangeResult {

    private Binance bitfinex;
    private  BinanceWebSocketClient socketClient;

    public BinanceConnector() throws URISyntaxException {

    }

    @Override
    public void start() {
        socketClient.connect();
    }

    @Override
    public void onResult(Exchange exchange) {
        this.onExchangeUpdated((Binance) exchange);
    }
}
