package br.com.stone.challenge.facts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.stone.domain.Fact
import br.com.stone.domain.FactsSource


class FactsViewModel(private val factsSource: FactsSource) : ViewModel() {

    val facts = MutableLiveData<List<Fact>>()

    fun search(term: String) {
        factsSource.search(term)
            .subscribe(
                { facts.postValue(it) },
                {}
            )
    }

}