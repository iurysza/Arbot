package src.base;

import java.util.ArrayList;
import java.util.List;

public class OrderBook {
    public final List<Order> asks = new ArrayList<>();
    public final List<Order> bids = new ArrayList<>();
}
