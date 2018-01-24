package src.binance.data.rest.binanceapi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import src.Log;

public class Intercep implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.debug(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
        Log.debug(String.format("REQUEST BODY BEGIN\n%s\nREQUEST BODY END", bodyToString(request)));

        Response response = chain.proceed(request);

        ResponseBody responseBody = response.body();
        String responseBodyString = response.body().string();

        // now we have extracted the response body but in the process
        // we have consumed the original reponse and can't read it again
        // so we need to build a new one to return from this method

        Response newResponse = response.newBuilder().body(ResponseBody.create(responseBody.contentType(), responseBodyString.getBytes())).build();

        long t2 = System.nanoTime();
        Log.debug(String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        Log.debug(String.format("RESPONSE BODY BEGIN:\n%s\nRESPONSE BODY END", responseBodyString));

        return newResponse;
    }

    private static String bodyToString(final Request request) {

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            if (copy != null) {
                if (copy.body() != null) {
                    copy.body().writeTo(buffer);
                }

            }
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}