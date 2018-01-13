package src.data;


import src.Bitfinex;

public class BitfinexConnector extends ExchangeConnector<Bitfinex> {

    private final Bitfinex bitfinex;

    public BitfinexConnector() {
        bitfinex = new Bitfinex();
    }

    @Override
    public void start() {

    }
}
