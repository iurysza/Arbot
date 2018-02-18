package src.binance.data.rest.binanceapi

import com.google.gson.annotations.SerializedName

data class BookSnap(@SerializedName("asks")
                    var asks: List<List<String>> = emptyList(),
                    @SerializedName("bids")
                    var bids: List<List<String>> = emptyList(),
                    @SerializedName("lastUpdateId") var lastUpdateId: Long = 0)


