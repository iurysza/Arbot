package src.hitbtc;

import src.base.Coin;
import src.base.Exchange;
import src.binance.data.ExchangeConnector;

import java.net.URISyntaxException;

public class HitbtcConnector extends ExchangeConnector<Hitbtc> implements ExchangeConnector.ExchangeResult{
    private Hitbtc hitbtc;
    private HitbtcWebSocketClient socketClient;

    public HitbtcConnector(Coin coin) {
        try {
            hitbtc = new Hitbtc();
            socketClient = new HitbtcWebSocketClient(hitbtc, coin, this);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        socketClient.connect();
    }

    @Override
    public void onResult(Exchange exchange) {
        this.onExchangeUpdated((Hitbtc) exchange);
    }
}
