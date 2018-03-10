package src.binance

import src.ExchangeConnector
import src.ExchangeConnector.ExchangeResult
import src.base.Coin
import src.base.Exchange
import src.binance.data.rest.RestConnectionManager
import src.binance.data.rest.ServiceConfig
import src.binance.data.rest.binanceapi.BinanceServices

class BinanceConnector(binance: Binance,
                       private val config: BinanceConfig,
                       private val connectionManager: RestConnectionManager<BinanceServices>) : ExchangeConnector<Binance>(), ExchangeResult {

    private val webSocketClient = BinanceWebSocketClient(binance, config, this)

    override fun onResult(exchange: Exchange?) {
        this.onExchangeUpdated(exchange as Binance)
    }

    override fun start() {
        connectionManager
                .service
                .loadBookSnap(config.fromCoin + config.toCoin, config.getLimit(150))
                .subscribe(
                        {
                            webSocketClient.onSnapshotLoaded(it)
                            webSocketClient.connect()
                        },
                        { error -> print(error) })
    }

    companion object Default {
        fun with(fromCoin: Coin): BinanceConnector {

            val binanceConfig = BinanceConfig(fromCoin, Coin.BTC)
            val serviceConfig = ServiceConfig(BinanceConfig.ORDER_BOOK_SNAPSHOT, BinanceServices::class.java)
            val restConnectionManager = RestConnectionManager.with(serviceConfig)

            return BinanceConnector(Binance(), binanceConfig, restConnectionManager)
        }
    }

}