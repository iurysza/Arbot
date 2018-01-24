package src.binance;

import java.net.URI;
import java.util.List;
import java.util.Map;

import io.reactivex.subscribers.DefaultSubscriber;
import src.Log;
import src.base.Coin;
import src.base.Exchange;
import src.base.OrderBook;
import src.binance.data.rest.RestConnectionManager;
import src.binance.data.rest.ServiceConfig;
import src.binance.data.rest.binanceapi.BinanceServices;
import src.binance.data.rest.binanceapi.BookSnap;

import static src.base.Coin.BTC;
import static src.base.Coin.LTC;


public class Binance extends Exchange {

    BinanceWebSocketClient webSocketClient;

    public Binance(BinanceConfig binanceConfig) {
        webSocketClient = new BinanceWebSocketClient(binanceConfig.getOrdebookUpdate());
    }


    @Override
    public String getName() {
        return "Binance";
    }

    @Override
    public double getMakerFee() {
        return 0.001;
    }

    @Override
    public double getTakerFee() {
        return 0.001;
    }


    @Override
    protected void fillTransferFees(Map<Coin, Double> transferFees) {
        transferFees.put(Coin.BTC, 0.001);
        transferFees.put(Coin.LTC, 0.01);
        transferFees.put(Coin.IOTA, 0.05);
    }

    @Override
    public OrderBook getOrderBook(Coin coin) {
        return super.getOrderBook(coin);
    }

    public static void main(String[] args) {
        BinanceConfig binanceConfig = new BinanceConfig(LTC, BTC);
        URI defaultBinanceOrderBook = binanceConfig.getOrdebookUpdate();
        BinanceWebSocketClient binanceWebSocketClient = new BinanceWebSocketClient(defaultBinanceOrderBook);
        binanceWebSocketClient.connect();

        //noinspection unchecked
        ServiceConfig<BinanceServices> binanceService = new ServiceConfig(BinanceConfig.Companion.getORDER_BOOK_SNAPSHOT(), BinanceServices.class);


        RestConnectionManager<BinanceServices> connectionManager =
                RestConnectionManager.Companion.createRestConnectionManager(binanceService);
        connectionManager
                .getRetrofitService()
                .loadBookSnap("BNBBTC", 1000)
                .subscribe(new DefaultSubscriber<List<BookSnap>>() {
                    @Override
                    public void onNext(List<BookSnap> bookSnaps) {
                        Log.debug(bookSnaps.toString());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.debug(t.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
