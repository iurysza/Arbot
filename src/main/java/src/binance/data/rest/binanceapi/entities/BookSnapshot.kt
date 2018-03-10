package src.binance.data.rest.binanceapi.entities

import com.google.gson.annotations.SerializedName

data class BookSnapshot(
        @SerializedName("asks")
        var asks: List<List<String>> = emptyList(),
        @SerializedName("bids")
        var bids: List<List<String>> = emptyList(),
        @SerializedName("lastUpdateId") var lastUpdateId: Long = 0)


