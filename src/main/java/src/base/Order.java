package src.base;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    @SerializedName("price")
    private Double price;
    @SerializedName("size")
    private Double amount;

    @Override
    public String toString(){
        return String.format("P: %.8f, A: %f, T: %.8f", price, amount, getBtcAmount());
    }

    public double getBtcAmount() {
        return price * amount;
    }

}
