package br.com.stone.data.di

import br.com.stone.data.FactsDataSource
import br.com.stone.domain.FactsSource
import org.koin.dsl.module.module

val dataModule = module {

    single<FactsSource> { FactsDataSource() }

}