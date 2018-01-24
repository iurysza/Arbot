package src.base;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    private Double price;
    private Double amount;

    @Override
    public String toString(){
        return String.format("P: %.8f, A: %f", price, amount);
    }
}
