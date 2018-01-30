package src.base;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderBook {
    @SerializedName("asks")
    public  List<Order> asks = new ArrayList<>();
    @SerializedName("bids")
    public  List<Order> bids = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder("|            BID            |            ASK            |\n");
        int size = Math.min(bids.size(), asks.size());
        size = Math.min(10, size);
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
