package src.binance;

import src.base.Coin;
import src.base.Exchange;
import src.binance.data.ExchangeConnector;
import src.binance.data.rest.RestConnectionManager;
import src.binance.data.rest.ServiceConfig;
import src.binance.data.rest.binanceapi.BinanceServices;

import static src.base.Coin.BTC;

public class BinanceConnector extends ExchangeConnector<Binance> implements ExchangeConnector.ExchangeResult {

    private Binance binance;
    private BinanceWebSocketClient binanceWebSocketClient;
    private RestConnectionManager<BinanceServices> connectionManager;

    public BinanceConnector(Binance binance, BinanceWebSocketClient binanceWebSocketClient, RestConnectionManager<BinanceServices> connectionManager) {
        this.binance = binance;
        this.binanceWebSocketClient = binanceWebSocketClient;
        this.connectionManager = connectionManager;
//        this.binanceWebSocketClient.setExchangeResult(this);
    }

    @Override
    public void start() {
//        connectionManager.getRetrofitService().loadBookSnap();
        binanceWebSocketClient.connect();


        //REST API GOES HERE

    }

    @Override
    public void onResult(Exchange exchange) {
        this.onExchangeUpdated((Binance) exchange);


    }

    public static BinanceConnector createDefaultConnector(Coin coin) {
        BinanceConfig config = new BinanceConfig(coin, BTC);
        Binance binance = new Binance();
        BinanceWebSocketClient binanceWebSocketClient = new BinanceWebSocketClient(binance,config,null);

        //noinspection unchecked
        ServiceConfig<BinanceServices> binanceService = new ServiceConfig(BinanceConfig.ORDER_BOOK_SNAPSHOT, BinanceServices.class);
        RestConnectionManager<BinanceServices> connectionManager = RestConnectionManager.Builder.createRestConnectionManager(binanceService);

        return new BinanceConnector(binance,binanceWebSocketClient, connectionManager);
    }
}
