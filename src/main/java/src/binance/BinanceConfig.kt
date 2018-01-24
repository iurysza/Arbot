package src.binance

import src.base.Coin
import java.net.URI

class BinanceConfig @JvmOverloads constructor(
         val fromCoin: Coin,
         val toCoin: Coin
) {

    val ordebookUpdate: URI
        get() {
            val url = ORDER_BOOK_UPDATE +
                    formatCoin(fromCoin) +
                    formatCoin(toCoin) +
                    DEPTH

            return URI.create(url)
        }



    private fun formatCoin(coin: Coin): String {
        return coin.toString().toLowerCase()
    }

    companion object {
        public val ORDER_BOOK_UPDATE = "wss://stream.binance.com:9443/ws/"
        public val ORDER_BOOK_SNAPSHOT = "https://www.binance.com/api/v1/"
        private val DEPTH = "@depth"
    }

}
