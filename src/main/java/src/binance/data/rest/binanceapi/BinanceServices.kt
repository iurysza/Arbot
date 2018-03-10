package src.binance.data.rest.binanceapi

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import src.binance.BinanceConfig
import src.binance.data.rest.binanceapi.entities.BookSnapshot

interface BinanceServices {
    @GET("depth")
    fun loadBookSnap(
            @Query("symbol") symbol: String,
            @Query("limit") limit: Int = BinanceConfig.LIMIT.DEFAULT.value
    ): Flowable<BookSnapshot>

}
