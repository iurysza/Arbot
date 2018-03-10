package src.base;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.CopyOnWriteArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderBook {
    @SerializedName("asks")
    public CopyOnWriteArrayList<Order> asks = new CopyOnWriteArrayList<>();
    @SerializedName("bids")
    public CopyOnWriteArrayList<Order> bids = new CopyOnWriteArrayList<>();

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
