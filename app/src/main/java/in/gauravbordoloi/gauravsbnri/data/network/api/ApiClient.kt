package `in`.gauravbordoloi.gauravsbnri.data.network.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private val BASE_URL = "https://api.github.com/"
    private var retrofit: Retrofit? = null
    private val DEFAULT_TIMEOUT = 30.toLong()

    fun getClient(): Retrofit {
        if (retrofit == null) {
            val client = OkHttpClient.Builder()
            client.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            client.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            client.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
        }
        return retrofit!!
    }

}