package br.com.stone.domain.di

import br.com.stone.domain.interactor.GetHistoricListUseCase
import br.com.stone.domain.interactor.GetSuggestionListUseCase
import br.com.stone.domain.interactor.SearchFactsUseCase
import br.com.stone.domain.interactor.UpdateSuggestionsCacheUseCase
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module.module

val useCaseModule = module {

    factory {
        SearchFactsUseCase(
            factRepository = get(),
            historicRepository = get(),
            scheduler = Schedulers.io()
        )
    }

    factory {
        GetHistoricListUseCase(
            historicRepository = get(),
            scheduler = Schedulers.io()
        )
    }

    factory {
        GetSuggestionListUseCase(
            categoryRepository = get(),
            scheduler = Schedulers.io()
        )
    }

    factory {
        UpdateSuggestionsCacheUseCase(
                categoryRepository = get(),
                scheduler = Schedulers.io()
        )
    }

}

val domainModules = listOf(useCaseModule)