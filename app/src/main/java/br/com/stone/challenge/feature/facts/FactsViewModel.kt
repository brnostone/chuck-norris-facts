package br.com.stone.challenge.feature.facts

import androidx.lifecycle.MutableLiveData
import br.com.stone.challenge.util.RxViewModel
import br.com.stone.domain.FactsSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers


class FactsViewModel(private val factsSource: FactsSource) : RxViewModel() {

    val facts = MutableLiveData<List<FactScreen>>()

    fun search(query: String) {
        disposables += factsSource.search(query)
            .map { FactScreenMapper.map(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { facts.postValue(it) },
                { it.printStackTrace() }
            )
    }

}