package br.com.stone.data.di

import br.com.stone.data.FactsDataSource
import br.com.stone.data.local.CacheService
import br.com.stone.data.local.PreferenceService
import br.com.stone.data.remote.RemoteService
import br.com.stone.data.remote.api.ChuckApi
import br.com.stone.domain.FactsSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {

    factory { RemoteService(chuckApi = get()) }
    factory<CacheService> { PreferenceService() }

    single<FactsSource> {
        FactsDataSource(
            remoteService = get(),
            cacheService = get()
        )
    }

    factory {
        val client = get<OkHttpClient>()
        val gson = get<Gson>()

        Retrofit.Builder()
            .baseUrl(ChuckApi.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(ChuckApi::class.java)
    }

    factory {
        OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    single { GsonBuilder().create() }

}