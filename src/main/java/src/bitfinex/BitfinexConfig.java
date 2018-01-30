package src.bitfinex;

import src.base.Coin;

public class BitfinexConfig {
    public final static String uri = "wss://api.bitfinex.com/ws/";
    private PAIR pair;
    private PRECISION precision;
    private FREQUENCY frequency;
    private Integer length;

    public static enum PAIR {
        BTCUSD, LTCUSD, LTCBTC, ETHUSD, ETHBTC, IOTBTC, TRXBTC, SNGBTC
    }

    /**
     * Level of price aggregation (P0, P1, P2, P3).
     * The default is P0.
     */
    public static enum PRECISION {
        P0, P1, P2, P3
    }

    /**
     * Frequency of updates (F0, F1).
     * F0=realtime / F1=2sec.
     * The default is F0.
     **/
    public static enum FREQUENCY {
        F0, F1
    }

    private BitfinexConfig(PAIR pair, PRECISION precision, FREQUENCY frequency, Integer length) {
        this.pair = pair;
        this.precision = precision;
        this.frequency = frequency;
        this.length = length;
    }


    public static BitfinexConfig getDefaultConfig() {
        return new BitfinexConfig(PAIR.BTCUSD, PRECISION.P0, FREQUENCY.F0, 25);
    }

    private void setPair(PAIR pair) {
        this.pair = pair;
    }

    public static BitfinexConfig getConfigByCoin(Coin coin) throws Exception {
        BitfinexConfig config = BitfinexConfig.getDefaultConfig();
        switch (coin) {
            case IOTA:
                config.setPair(PAIR.IOTBTC);
                break;
            case LTC:
                config.setPair(PAIR.LTCBTC);
                break;
            case BTC:
                config.setPair(PAIR.BTCUSD);
                break;
            case ETH:
                config.setPair(PAIR.ETHBTC);
                break;
            case TRX:
                config.setPair(PAIR.TRXBTC);
                break;
            case SNGLS:
                config.setPair(PAIR.SNGBTC);
                break;
            default:
                throw new Exception("Invalid coin " + coin);
        }

        return config;
    }

    public String generateJSON() {
        return "{" +
                "\"event\": \"subscribe\"," +
                "\"channel\": \"book\"," +
                "\"pair\": \"" + this.pair + "\"," +
                "\"prec\": \"" + this.precision + "\"," +
                "\"freq\": \"" + this.frequency + "\"," +
                "\"length\": \"" + this.length + "\"" +
                "}";
    }
}
