package src.binance.xtensions

import src.base.Order

internal fun List<String>.toOrder() = Order(get(0).toDouble(), get(1).toDouble())
