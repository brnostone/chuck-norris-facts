package br.com.stone.data.di

import br.com.stone.data.local.CacheService
import br.com.stone.data.local.PreferenceService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val dataLocalModule = module {

    single<CacheService> { PreferenceService(androidContext()) }

}