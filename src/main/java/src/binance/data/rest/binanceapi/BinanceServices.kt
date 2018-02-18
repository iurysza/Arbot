package src.binance.data.rest.binanceapi

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface BinanceServices {
    @GET("depth")
    fun loadBookSnap(
            @Query("symbol") symbol: String,
            @Query("limit") limit: Int = 5
    ): Flowable<BookSnap>

}
