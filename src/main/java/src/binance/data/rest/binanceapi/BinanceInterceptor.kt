package src.binance.data.rest.binanceapi

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import src.Log
import java.io.IOException

class BinanceInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val t1 = System.nanoTime()
        Log.debug(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()))
        Log.debug(String.format("REQUEST BODY BEGIN\n%s\nREQUEST BODY END", bodyToString(request)))

        val response = chain.proceed(request)

        val responseBody = response.body()
        val responseBodyString = response.body()!!.string()

        // now we have extracted the response body but in the process
        // we have consumed the original reponse and can't read it again
        // so we need to with a new one to return from this method

        val newResponse = response.newBuilder().body(ResponseBody.create(responseBody!!.contentType(), responseBodyString.toByteArray())).build()

        val t2 = System.nanoTime()
        Log.debug(String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6, response.headers()))
        Log.debug(String.format("RESPONSE BODY BEGIN:\n%s\nRESPONSE BODY END", responseBodyString))

        return newResponse
    }

    private fun bodyToString(request: Request): String {

        try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            if (copy != null) {
                if (copy.body() != null) {
                    copy.body()!!.writeTo(buffer)
                }

            }
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "did not work"
        }

    }
}