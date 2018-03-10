package src.binance.xtensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import src.binance.data.rest.binanceapi.entities.DepthDiff

fun Gson.getDepthDiffFrom(message: String): DepthDiff {
    return this.fromJson<DepthDiff>(message,  object : TypeToken<DepthDiff>() {}.type)
}
