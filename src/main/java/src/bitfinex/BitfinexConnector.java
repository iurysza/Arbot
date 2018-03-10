package src.bitfinex;


import src.base.Coin;
import src.base.Exchange;
import src.ExchangeConnector;

import static src.base.Coin.BTC;

public class BitfinexConnector extends ExchangeConnector<Bitfinex> implements ExchangeConnector.ExchangeResult {

    private Bitfinex bitfinex;
    private BitfinexWebSocketClient socketClient;

    public BitfinexConnector(Coin coin) {
        try {
            bitfinex = new Bitfinex();
            bitfinex.setWallet(coin, 10000);
            bitfinex.setWallet(BTC, 0.1);
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
