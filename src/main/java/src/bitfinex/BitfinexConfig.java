package src.bitfinex;

public class BitfinexConfig {
    public final static String uri = "wss://api.bitfinex.com/ws/";
    private PAIR pair;
    private PRECISION precision;
    private FREQUENCY frequency;
    private Integer length;

    public static enum PAIR {
        BTCUSD, LTCUSD, LTCBTC, ETHUSD, ETHBTC
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

    public static BitfinexConfig getDefaultConfig(){
        return new BitfinexConfig(PAIR.BTCUSD, PRECISION.P0, FREQUENCY.F0, 25);
    }

    public String generateJSON(){
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