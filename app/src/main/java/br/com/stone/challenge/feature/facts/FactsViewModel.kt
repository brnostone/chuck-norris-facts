package br.com.stone.challenge.feature.facts

import androidx.lifecycle.MutableLiveData
import br.com.stone.challenge.feature.common.StateMachine
import br.com.stone.challenge.feature.common.ViewState
import br.com.stone.challenge.util.RxViewModel
import br.com.stone.domain.FactsSource
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers


class FactsViewModel(private val factsSource: FactsSource) : RxViewModel() {

    val state = MutableLiveData<ViewState<List<FactScreen>>>()

    fun search(query: String) {
        disposables += factsSource.search(query)
                .map { FactScreenMapper.map(it) }
                .compose(StateMachine())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { state.postValue(it) },
                        { it.printStackTrace() }
                )
    }

}