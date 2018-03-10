package src.binance.data.rest

class ServiceConfig<out RestService> constructor(
        val baseUrl: String,
        val restServiceClass: Class<out RestService>
)
