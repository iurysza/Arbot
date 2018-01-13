package src.data;


import src.Binance;

public class BinanceConnector extends ExchangeConnector<Binance> {

    private final Binance binance;

    public BinanceConnector() {
        binance = new Binance();
    }

    @Override
    public void start() {

    }
}
