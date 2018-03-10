package src.binance

import src.base.Coin
import src.base.Exchange


class Binance : Exchange() {

    override fun getName(): String {
        return "Binance"
    }

    override fun getMakerFee(): Double {
        return 0.001
    }

    override fun getTakerFee(): Double {
        return 0.001
    }

    override fun fillTransferFees(transferFees: MutableMap<Coin, Double>) {
        transferFees[Coin.BTC] = 0.001
        transferFees[Coin.LTC] = 0.01
        transferFees[Coin.IOTA] = 0.05
    }

}
