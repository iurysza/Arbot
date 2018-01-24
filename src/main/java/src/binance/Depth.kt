package src.binance

import com.google.gson.annotations.SerializedName


class Depth {
    @SerializedName("asks")
    val asks: List<List<String>>? = emptyList()
    @SerializedName("bids")
    val bids: List<List<String>>? = emptyList()
    @SerializedName("lastUpdateId")
    val lastUpdateId: Long? = 0

}
