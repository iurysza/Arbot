package src.hitbtc;

import src.base.Coin;

import java.util.Random;

public class HitbtcConfig {
    public final static String uri = "wss://api.hitbtc.com/api/2/ws";
    private String pair;
    private String method;

    private HitbtcConfig(Coin coin, String method) {
        this.pair = coin.toString().toUpperCase() + "BTC";
        this.method = method;
    }

    public static HitbtcConfig getOrderBookConfig(Coin coin) {
        return new HitbtcConfig(coin, "subscribeOrderbook");
    }

    public String generateJSON() {
        return "{\n" +
                "  \"method\": \"" + this.method + "\",\n" +
                "  \"params\": {\n" +
                "    \"symbol\": \"" + this.pair + "\"\n" +
                "  },\n" +
                "  \"id\": "+new Random().nextInt(1000)+"\n" +
                "}";
    }
}
