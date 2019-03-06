package br.com.stone.data.di

import br.com.stone.data.FactsDataSource
import br.com.stone.domain.FactsSource
import org.koin.dsl.module.module

val dataSourceModule = module {

    single<FactsSource> {
        FactsDataSource(
            remoteService = get(),
            cacheService = get()
        )
    }

}

val dataModules = listOf(dataSourceModule, dataLocalModule, dataRemoteModule)