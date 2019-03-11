package br.com.stone.challenge.feature.facts

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import br.com.stone.challenge.feature.common.StateMachine
import br.com.stone.challenge.feature.common.ViewState
import br.com.stone.challenge.util.RxViewModel
import br.com.stone.domain.interactor.SearchFactsUseCase
import br.com.stone.domain.interactor.UpdateSuggestionsCacheUseCase
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber


class FactsViewModel(
        private val searchUseCase: SearchFactsUseCase,
        private val updateSuggestionsUseCase: UpdateSuggestionsCacheUseCase) : RxViewModel(), LifecycleObserver {

    val state = MutableLiveData<ViewState<List<FactScreen>>>().apply {
        value = ViewState.Default
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun updateSuggestions() {
        disposables += updateSuggestionsUseCase.execute()
                .subscribe(
                        {},
                        { Timber.e(it) }
                )
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