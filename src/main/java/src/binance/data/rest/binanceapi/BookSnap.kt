package src.binance.data.rest.binanceapi

import com.google.gson.annotations.SerializedName

class BookSnap {
    @SerializedName("asks")
    var asks: List<List<String>>? = null
    @SerializedName("bids")
    var bids: List<List<String>>? = null
    @SerializedName("lastUpdateId")
    var lastUpdateId: Long? = null
}
