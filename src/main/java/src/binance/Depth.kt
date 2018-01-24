package src.binance

import com.google.gson.annotations.SerializedName


class Depth {
    @SerializedName("asks")
    private val asks: List<List<String>>? = emptyList()
    @SerializedName("bids")
    private val bids: List<List<String>>? = emptyList()
    @SerializedName("lastUpdateId")
    private val lastUpdateId: Long? = 0

}
