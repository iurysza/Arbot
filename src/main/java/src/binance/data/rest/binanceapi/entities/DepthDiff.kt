package src.binance.data.rest.binanceapi.entities

import com.google.gson.annotations.SerializedName
import src.base.Order
import src.binance.xtensions.toOrder
import kotlin.reflect.KProperty


class StringOrder( asks: List<List<String>>){
   var asksOrder : List<Order> = asks.map { it.toOrder() }
    operator fun getValue(thisRef: DepthDiff, property: KProperty<*>): List<Order>{
        return asksOrder
    }
}

data class DepthDiff(
        @SerializedName("a") var asksToUpdate: List<List<String>> = emptyList(),
        @SerializedName("b") var bidsToUpdate: List<List<String>> = emptyList(),
        @SerializedName("u") var finalUpdateId: Long = -1,
        @SerializedName("U") var firstUpdateId: Long = -1,
        @SerializedName("s") var symbol: String,
        @SerializedName("E") var eventTime: Long = -1,
        @SerializedName("e") var eventType: String? = null) {

    val askOrders by StringOrder(asksToUpdate)
    val bidOrders by StringOrder(bidsToUpdate)

}

