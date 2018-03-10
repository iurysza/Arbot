package src.binance

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import src.ExchangeConnector
import src.Utils
import src.binance.data.rest.binanceapi.entities.BookSnapshot
import src.binance.xtensions.getDepthDiffFrom
import kotlin.properties.Delegates


/**
 * This example demonstrates how to create a websocket connection to a server. Only the most important callbacks are overloaded.
 */
class BinanceWebSocketClient(
        private val binance: Binance,
        private val config: BinanceConfig,
        private val exchangeResult: ExchangeConnector.ExchangeResult) : WebSocketClient(config.ordebookUpdate) {
    private lateinit var bookSnapshot: BookSnapshot


    val number: Int by Delegates.notNull()

    override fun onOpen(handshakedata: ServerHandshake) {
        println("opened connection: ${handshakedata.httpStatusMessage}")
    }

    override fun onMessage(message: String) {
        println("received: $message")

        val diffFrom = Utils.gson.getDepthDiffFrom(message)
        diffFrom.askOrders
        diffFrom.bidOrders



//        todo update orderbook with changes
//        val orderBook = binance.getOrderBook(config.fromCoin)
//        exchangeResult.onResult(binance)
    }


    override fun onClose(code: Int, reason: String, remote: Boolean) {
        println("Connection closed by ${if (remote) "remote peer" else "us"} Code: $code Reason: $reason")
    }

    override fun onError(ex: Exception) {
        ex.printStackTrace()
    }

    fun onSnapshotLoaded(bookSnapshot: BookSnapshot) {
        this.bookSnapshot = bookSnapshot
        //todo update exchange with orderbook snapshot
    }
}
