package src.binance

import com.google.gson.reflect.TypeToken
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import src.Utils
import src.base.Order
import src.binance.data.ExchangeConnector
import src.binance.data.rest.binanceapi.BookSnap
import java.util.*


/**
 * This example demonstrates how to create a websocket connection to a server. Only the most important callbacks are overloaded.
 */
class BinanceWebSocketClient(private val binance: Binance, private val config: BinanceConfig, private val exchangeResult: ExchangeConnector.ExchangeResult) : WebSocketClient(config.ordebookUpdate) {
    private lateinit var bookSnap: BookSnap

    override fun onOpen(handshakedata: ServerHandshake) {
        println("opened connection")

        // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
    }

    override fun onMessage(message: String) {
        println("received: " + message)

        val depth = Utils.gson.fromJson<Depth>(message, object : TypeToken<Depth>() {}.type)

        val askList = getOrders(depth.asks!!)
        val bidList = getOrders(depth.bids!!)

        val orderBook = binance.getOrderBook(config.fromCoin)

        exchangeResult.onResult(binance)

    }

    private fun getOrders(items: List<List<String>>): List<Order> {
        val orderList = ArrayList<Order>()
        for (ask in items) {
            orderList += Order(ask[0].toDouble(), ask[1].toDouble())
        }
        return orderList
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        // The codecodes are documented in class org.java_websocket.framing.CloseFrame
        println("Connection closed by " + (if (remote) "remote peer" else "us") + " Code: " + code + " Reason: " + reason)
    }

    override fun onError(ex: Exception) {
        ex.printStackTrace()
        // if the error is fatal then onClose will be called additionally
    }


    fun onSnapshotLoaded(bookSnap: BookSnap) {
        this.bookSnap = bookSnap
    }
}
