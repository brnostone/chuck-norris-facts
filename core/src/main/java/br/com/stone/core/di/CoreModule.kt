package br.com.stone.core.di

import br.com.stone.core.facts.domain.FactsSource
import br.com.stone.core.facts.infrastructure.FactsInfrastructure
import br.com.stone.core.facts.presentation.FactsViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val coreModule = module {

    factory<FactsSource> { FactsInfrastructure() }

    viewModel { FactsViewModel(factsSource = get()) }

}