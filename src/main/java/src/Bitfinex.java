package src;

import java.util.Map;

import src.base.Coin;
import src.base.Exchange;

import static src.base.Coin.BTC;
import static src.base.Coin.IOTA;
import static src.base.Coin.LTC;

public class Bitfinex extends Exchange {


    @Override
    public String getName() {
        return "Bitfinex";
    }

    @Override
    public double getMakerFee() {
        return 0.001;
    }

    @Override
    public double getTakerFee() {
        return 0.002;
    }

    @Override
    protected void fillTransferFees(Map<Coin, Double> transferFees) {
        transferFees.put(BTC, 0.0008);
        transferFees.put(LTC, 0.001);
        transferFees.put(IOTA, 0.5);
    }
}
