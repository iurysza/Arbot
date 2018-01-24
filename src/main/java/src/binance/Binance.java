package src.binance;

import java.util.Map;

import src.base.Coin;
import src.base.Exchange;
import src.base.OrderBook;


public class Binance extends Exchange {



    public Binance() {
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

//
//        connectionManager
//                .getRetrofitService()
//                .loadBookSnap("BNBBTC", 1000)
//                .subscribe(new DefaultSubscriber<List<BookSnap>>() {
//                    @Override
//                    public void onNext(List<BookSnap> bookSnaps) {
//                        Log.debug(bookSnaps.toString());
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        Log.debug(t.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });


    }
}
