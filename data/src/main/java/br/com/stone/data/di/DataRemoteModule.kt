package br.com.stone.data.di

import br.com.stone.data.remote.RemoteDataSource
import br.com.stone.data.remote.RemoteSource
import br.com.stone.data.remote.api.ChuckApi
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataRemoteModule = module {

    factory { createOkHttpClient() }
    single {
        createWebService<ChuckApi>(
            okHttpClient = get(),
            url = ChuckApi.API_URL
        )
    }

    factory<RemoteSource> { RemoteDataSource(chuckApi = get()) }

}

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()

    return retrofit.create(T::class.java)
}
