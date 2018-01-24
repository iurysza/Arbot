package src.base;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    private double price;
    private double amount;

    public double getBtcAmount() {
        return price * amount;
    }
}
