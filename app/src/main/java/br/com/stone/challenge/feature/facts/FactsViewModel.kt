package br.com.stone.challenge.feature.facts

import androidx.lifecycle.MutableLiveData
import br.com.stone.challenge.feature.common.StateMachine
import br.com.stone.challenge.feature.common.ViewState
import br.com.stone.challenge.util.RxViewModel
import br.com.stone.domain.interactor.SearchFactsUseCase
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber


class FactsViewModel(private val searchUseCase: SearchFactsUseCase) : RxViewModel() {

    val state = MutableLiveData<ViewState<List<FactScreen>>>().apply {
        value = ViewState.Default
    }

    fun search(query: String) {
        disposables += searchUseCase.execute(query)
                .map { FactScreenMapper.map(it) }
                .compose(StateMachine())
                .subscribe(
                        { state.postValue(it) },
                        { Timber.e(it) }
                )
    }

}