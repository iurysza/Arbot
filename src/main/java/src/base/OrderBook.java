package src.base;

import com.google.gson.annotations.SerializedName;
import src.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderBook {
    @SerializedName("asks")
    public final List<Order> asks = new ArrayList<>();
    @SerializedName("bids")
    public final List<Order> bids = new ArrayList<>();

    public void sortAsks() {
        Collections.sort(asks, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return (o1.getPrice() > o2.getPrice()) ? 1 : -1;
            }
        });
    }

    public void sortBids() {
        Collections.sort(bids, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return (o1.getPrice() < o2.getPrice()) ? 1 : -1;
            }
        });
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder("|            BID            |            ASK            |\n");
        int size = Math.min(bids.size(), asks.size());
        for(int i = 0;i < size;i++ ){
            Order bid = bids.get(i);
            Order ask = asks.get(i);
            text.append("| ")
                .append(bid.toString())
                .append(" | ")
                .append(ask.toString())
                .append(" |\n");
        }

        return text.toString();
    }
}
