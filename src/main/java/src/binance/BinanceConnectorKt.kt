package src.binance

import src.base.Coin
import src.base.Exchange
import src.binance.data.ExchangeConnector
import src.binance.data.ExchangeConnector.ExchangeResult
import src.binance.data.rest.RestConnectionManager
import src.binance.data.rest.ServiceConfig
import src.binance.data.rest.binanceapi.BinanceServices

class BinanceConnectorKt(var binance: Binance,
                         var config: BinanceConfig,
                         var connectionManager: RestConnectionManager<BinanceServices>) : ExchangeConnector<Binance>(), ExchangeResult {

    val webSocketClient = BinanceWebSocketClient(binance, config, this)

    override fun onResult(exchange: Exchange?) {
        this.onExchangeUpdated(exchange as Binance)
    }

    override fun start() {
        connectionManager.retrofitService
                .loadBookSnap(config.fromCoin.toString().plus(config.toCoin.toString()))
                .subscribe(
                        {
                            webSocketClient.onSnapshotLoaded(it)
                            webSocketClient.connect()
                        },
                        { error -> print(error) })
    }

    companion object Builder {
        fun createDefaultConnector(coin: Coin): BinanceConnectorKt {
            val binanceConfig = BinanceConfig(coin, Coin.BTC)
            val binance = Binance()
            val serviceConfig = ServiceConfig(BinanceConfig.ORDER_BOOK_SNAPSHOT, BinanceServices::class.java)
            val restConnectionManager = RestConnectionManager.createRestConnectionManager(serviceConfig)
            return BinanceConnectorKt(binance, binanceConfig, restConnectionManager)
        }
    }

}