package br.com.stone.challenge.di

import br.com.stone.challenge.feature.facts.FactsViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val presentationModule = module {

    viewModel { FactsViewModel(factsSource = get()) }

}