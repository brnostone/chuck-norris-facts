package br.com.stone.data.di

import br.com.stone.data.local.CategoryLocalDataSource
import br.com.stone.data.local.CategoryLocalSource
import br.com.stone.data.local.HistoricLocalDataSource
import br.com.stone.data.local.HistoricLocalSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val dataLocalModule = module {

    single<CategoryLocalSource> { CategoryLocalDataSource(androidContext()) }
    single<HistoricLocalSource> { HistoricLocalDataSource(androidContext()) }

}