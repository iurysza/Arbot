package src;

import src.base.Order;
import src.base.OrderBook;

public class OrderBooks {

    public static OrderBook createFakeOrderBookLTCBitfinex() {
        OrderBook orderBook = new OrderBook();

        orderBook.bids.add(new Order(0.01669, 100000.0));
        orderBook.asks.add(new Order(0.016569, 100000.0));
        return orderBook;
    }

    public static OrderBook createFakeOrderBookLTCBinance() {
        OrderBook orderBook = new OrderBook();

        orderBook.bids.add(new Order(0.016730, 100000.0));
        orderBook.asks.add(new Order(0.016659, 100000.0));
        return orderBook;
    }

    public static OrderBook createFakeOrderBook() {
        OrderBook orderBook = new OrderBook();

        orderBook.bids.add(new Order(0.00025291, 19.00000000));
        orderBook.bids.add(new Order(0.00025290, 350.00000000));
        orderBook.bids.add(new Order(0.00025289, 1152.00000000));
        orderBook.bids.add(new Order(0.00025283, 8.00000000));
        orderBook.bids.add(new Order(0.00025281, 380.00000000));
        orderBook.bids.add(new Order(0.00025280, 16041.00000000));
        orderBook.bids.add(new Order(0.00025278, 8.00000000));
        orderBook.bids.add(new Order(0.00025273, 8.00000000));
        orderBook.bids.add(new Order(0.00025267, 92.00000000));
        orderBook.bids.add(new Order(0.00025226, 36.00000000));

        orderBook.asks.add(new Order(0.00025300, 390.00000000));
        orderBook.asks.add(new Order(0.00025326, 354.00000000));
        orderBook.asks.add(new Order(0.00025345, 39.00000000));
        orderBook.asks.add(new Order(0.00025348, 20.00000000));
        orderBook.asks.add(new Order(0.00025350, 177.00000000));
        orderBook.asks.add(new Order(0.00025359, 22.00000000));
        orderBook.asks.add(new Order(0.00025383, 190.00000000));
        orderBook.asks.add(new Order(0.00025385, 50.00000000));
        orderBook.asks.add(new Order(0.00025388, 51.00000000));
        orderBook.asks.add(new Order(0.00025389, 196.00000000));

        return orderBook;
    }

    public static OrderBook createFakeOrderBookLowerPrice() {
        OrderBook orderBook = new OrderBook();

        double priceToDecrease = 0.00000290;
        orderBook.bids.add(new Order(0.00025291 - priceToDecrease, 19.00000000));
        orderBook.bids.add(new Order(0.00025290 - priceToDecrease, 350.00000000));
        orderBook.bids.add(new Order(0.00025289 - priceToDecrease, 1152.00000000));
        orderBook.bids.add(new Order(0.00025283, 8.00000000));
        orderBook.bids.add(new Order(0.00025281, 380.00000000));
        orderBook.bids.add(new Order(0.00025280, 16041.00000000));
        orderBook.bids.add(new Order(0.00025278, 8.00000000));
        orderBook.bids.add(new Order(0.00025273, 8.00000000));
        orderBook.bids.add(new Order(0.00025267, 92.00000000));
        orderBook.bids.add(new Order(0.00025226, 36.00000000));

        orderBook.asks.add(new Order(0.00025300 - priceToDecrease, 390.00000000));
        orderBook.asks.add(new Order(0.00025326 - priceToDecrease, 354.00000000));
        orderBook.asks.add(new Order(0.00025345 - priceToDecrease, 39.00000000));
        orderBook.asks.add(new Order(0.00025348 - priceToDecrease, 20.00000000));
        orderBook.asks.add(new Order(0.00025350, 177.00000000));
        orderBook.asks.add(new Order(0.00025359, 22.00000000));
        orderBook.asks.add(new Order(0.00025383, 190.00000000));
        orderBook.asks.add(new Order(0.00025385, 50.00000000));
        orderBook.asks.add(new Order(0.00025388, 51.00000000));
        orderBook.asks.add(new Order(0.00025389, 196.00000000));

        return orderBook;
    }
}
