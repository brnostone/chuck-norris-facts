package br.com.stone.challenge.facts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.stone.domain.Fact
import br.com.stone.domain.FactsSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class FactsViewModel(private val factsSource: FactsSource) : ViewModel() {

    val facts = MutableLiveData<List<Fact>>()

    fun search(query: String) {
        factsSource.search(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { facts.postValue(it) },
                { it.printStackTrace() }
            )
    }

}