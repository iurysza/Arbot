package src.binance

import src.base.Coin
import java.net.URI

class BinanceConfig(val fromCoin: Coin, val toCoin: Coin) {

    val ordebookUpdate: URI get() = URI.create("$ORDER_BOOK_UPDATE${formatCoin(fromCoin)}${formatCoin(toCoin)}$DEPTH")

    private fun formatCoin(coin: Coin): String {
        return coin.toString().toLowerCase()
    }

    companion object {
        const val ORDER_BOOK_UPDATE = "wss://stream.binance.com:9443/ws/"
        const val ORDER_BOOK_SNAPSHOT = "https://www.binance.com/api/v1/"
        const val DEPTH = "@depth"
    }

    enum class LIMIT(val value: Int) {
        FIRST(5),
        SECOND(10),
        THIRD(20),
        FOURTH(50),
        FITH(100),
        SITH(500),
        SEVENTH(1000),
        DEFAULT(10)
    }

    fun getLimit(num: Int): Int {
        return LIMIT
                .values()
                .find { it.value == num }
                ?.value
                ?: LIMIT.DEFAULT.value
                        .also { println("The Limit $num is invalid. Using Default Value: $it") }
    }
}
