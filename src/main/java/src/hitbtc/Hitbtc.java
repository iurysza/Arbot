package src.hitbtc;

import src.base.Coin;
import src.base.Exchange;

import java.util.Map;

public class Hitbtc extends Exchange{
    @Override
    public String getName() {
        return "HitBTC";
    }

    @Override
    public double getMakerFee() {
        return 0.01;
    }

    @Override
    public double getTakerFee() {
        return 0.1;
    }

    @Override
    protected void fillTransferFees(Map<Coin, Double> transferFees) {

    }
}
