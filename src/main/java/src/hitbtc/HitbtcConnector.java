package src.hitbtc;

import java.net.URISyntaxException;

import src.ExchangeConnector;
import src.base.Coin;
import src.base.Exchange;

import static src.base.Coin.BTC;

public class HitbtcConnector extends ExchangeConnector<Hitbtc> implements ExchangeConnector.ExchangeResult{
    private Hitbtc hitbtc;
    private HitbtcWebSocketClient socketClient;

    public HitbtcConnector(Coin coin) {
        try {
            hitbtc = new Hitbtc();
            hitbtc.setWallet(coin, 10000);
            hitbtc.setWallet(BTC, 0.1);
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
