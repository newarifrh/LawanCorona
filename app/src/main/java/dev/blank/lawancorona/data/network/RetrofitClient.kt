package dev.blank.lawancorona.data.network

import dev.blank.lawancorona.util.TLSSocketFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val url = "https://api.kawalcorona.com/"
    val service: Retrofit
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            val httpClient = OkHttpClient.Builder().addInterceptor(interceptor).sslSocketFactory(TLSSocketFactory())
            httpClient.addInterceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build()
                chain.proceed(request)
            }
            val client = httpClient.build()
            return Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
        }
}