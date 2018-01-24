package src.binance.data.rest

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import src.binance.data.rest.binanceapi.Intercep


class RestConnectionManager <T>{
    val retrofitService: T

    private constructor(retrofitService: T) {
        this.retrofitService = retrofitService
    }

    companion object {
        fun <T>createRestConnectionManager(serviceConfig: ServiceConfig<T>): RestConnectionManager<T> {
            val gson = GsonBuilder()
                    .setLenient()
                    .create()



            val client = OkHttpClient.Builder()
                    .addNetworkInterceptor(Intercep())
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(serviceConfig.baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            val restService = retrofit.create(serviceConfig.restServiceClass)

            return RestConnectionManager(restService)
        }
    }
}
