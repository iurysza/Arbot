package src.hitbtc;

import com.google.gson.annotations.SerializedName;
import src.base.Order;

import java.util.List;

public class HitbtcResponse {
    @SerializedName("jsonrpc")
    public String jsonrpc;
    @SerializedName("method")
    public String method;
    @SerializedName("params")
    public Params params;

    public static class Params {
        @SerializedName("ask")
        public List<Order> ask;
        @SerializedName("bid")
        public List<Order> bid;
        @SerializedName("symbol")
        public String symbol;
        @SerializedName("sequence")
        public int sequence;
    }
}
