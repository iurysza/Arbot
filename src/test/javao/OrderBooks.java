package javao;

import src.base.Order;
import src.base.OrderBook;

public class OrderBooks {

    public static OrderBook createFakeOrderBookLTCBitfinex() {
        OrderBook orderBook = new OrderBook();

        orderBook.bids.add(new Order(0.01669, 100000));
        orderBook.asks.add(new Order(0.016569, 100000));
        return orderBook;
    }

    public static OrderBook createFakeOrderBookLTCBinance() {
        OrderBook orderBook = new OrderBook();

        orderBook.bids.add(new Order(0.016730, 100000));
        orderBook.asks.add(new Order(0.016659, 100000));
        return orderBook;
    }

    public static OrderBook createFakeOrderBook() {
        OrderBook orderBook = new OrderBook();

        orderBook.bids.add(new Order(0.00025291, 19.00000000));
        orderBook.bids.add(new Order(0.00025290, 35.00000000));
        orderBook.bids.add(new Order(0.00025289, 1152.00000000));
        orderBook.bids.add(new Order(0.00025283, 8.00000000));
        orderBook.bids.add(new Order(0.00025281, 380.00000000));
        orderBook.bids.add(new Order(0.00025280, 16041.00000000));
        orderBook.bids.add(new Order(0.00025278, 8.00000000));
        orderBook.bids.add(new Order(0.00025273, 8.00000000));
        orderBook.bids.add(new Order(0.00025267, 92.00000000));
        orderBook.bids.add(new Order(0.00025226, 36.00000000));

        orderBook.asks.add(new Order(0.00025300, 39.00000000));
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

        double priceToDecrease = 0.00000050;
        orderBook.bids.add(new Order(0.00025291 - priceToDecrease, 19.00000000));
        orderBook.bids.add(new Order(0.00025290 - priceToDecrease, 35.00000000));
        orderBook.bids.add(new Order(0.00025289 - priceToDecrease, 1152.00000000));
        orderBook.bids.add(new Order(0.00025283 - priceToDecrease, 8.00000000));
        orderBook.bids.add(new Order(0.00025281 - priceToDecrease, 380.00000000));
        orderBook.bids.add(new Order(0.00025280 - priceToDecrease, 16041.00000000));
        orderBook.bids.add(new Order(0.00025278 - priceToDecrease, 8.00000000));
        orderBook.bids.add(new Order(0.00025273 - priceToDecrease, 8.00000000));
        orderBook.bids.add(new Order(0.00025267 - priceToDecrease, 92.00000000));
        orderBook.bids.add(new Order(0.00025226 - priceToDecrease, 36.00000000));

        orderBook.asks.add(new Order(0.00025300 - priceToDecrease, 39.00000000));
        orderBook.asks.add(new Order(0.00025326 - priceToDecrease, 354.00000000));
        orderBook.asks.add(new Order(0.00025345 - priceToDecrease, 39.00000000));
        orderBook.asks.add(new Order(0.00025348 - priceToDecrease, 20.00000000));
        orderBook.asks.add(new Order(0.00025350 - priceToDecrease, 177.00000000));
        orderBook.asks.add(new Order(0.00025359 - priceToDecrease, 22.00000000));
        orderBook.asks.add(new Order(0.00025383 - priceToDecrease, 190.00000000));
        orderBook.asks.add(new Order(0.00025385 - priceToDecrease, 50.00000000));
        orderBook.asks.add(new Order(0.00025388 - priceToDecrease, 51.00000000));
        orderBook.asks.add(new Order(0.00025389 - priceToDecrease, 196.00000000));

        return orderBook;
    }
}
