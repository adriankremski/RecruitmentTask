package com.github.snuffix.recruitmenttask.remote.network.service

import com.github.snuffix.recruitmenttask.data.repository.NoConnectivityException
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


object DocumentsServiceFactory {
    fun makeService(networkConfiguration: NetworkConfiguration, networkCheck: NetworkCheck): DocumentsService {
        val okHttpClient = makeOkHttpClient(networkConfiguration.cacheDir, networkCheck, makeLoggingInterceptor((networkConfiguration.isDebug)))
        return makeService(okHttpClient)
    }

    private fun makeService(okHttpClient: OkHttpClient): DocumentsService {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost/") // In retrofit 2 you need to put any url, even if you are not using it
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(DocumentsService::class.java)
    }

    private fun makeOkHttpClient(cacheDir: File, networkCheck: NetworkCheck, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val cacheSize = 10 * 1024 * 1024

        return OkHttpClient.Builder()
            .addInterceptor(makeNetworkCheckInterceptor(networkCheck))
            .cache(Cache(cacheDir, cacheSize.toLong()))
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    private fun makeNetworkCheckInterceptor(networkCheck: NetworkCheck): Interceptor = Interceptor { chain ->
        val builder = chain.request().newBuilder()

        if (!networkCheck.isOnline()) {
            throw NoConnectivityException()
        }

        chain.proceed(builder.build())
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}
