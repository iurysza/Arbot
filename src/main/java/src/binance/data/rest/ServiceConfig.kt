package src.binance.data.rest

class ServiceConfig<out RestService>@JvmOverloads constructor(
         val baseUrl: String,
         val restServiceClass: Class<out RestService>
) {
}
