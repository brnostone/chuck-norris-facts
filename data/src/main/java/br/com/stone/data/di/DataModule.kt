package br.com.stone.data.di

import br.com.stone.data.CategoryDataRepository
import br.com.stone.data.FactDataRepository
import br.com.stone.data.HistoricDataRepository
import br.com.stone.domain.repository.CategoryRepository
import br.com.stone.domain.repository.FactRepository
import br.com.stone.domain.repository.HistoricRepository
import org.koin.dsl.module.module

val repositoryModule = module {

    single<FactRepository> {
        FactDataRepository(
            remoteDataSource = get()
        )
    }

    single<CategoryRepository> {
        CategoryDataRepository(
            categoryLocalSource = get(),
            remoteDataSource = get()
        )
    }

    single<HistoricRepository> {
        HistoricDataRepository(
            historicLocalSource = get()
        )
    }

}

val dataModules = listOf(repositoryModule, dataLocalModule, dataRemoteModule)