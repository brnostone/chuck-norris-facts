package br.com.stone.challenge.feature.search

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import br.com.stone.challenge.feature.common.StateMachine
import br.com.stone.challenge.feature.common.ViewState
import br.com.stone.challenge.util.RxViewModel
import br.com.stone.challenge.util.extensions.toImmutable
import br.com.stone.domain.interactor.GetHistoricListUseCase
import br.com.stone.domain.interactor.GetSuggestionListUseCase
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber

class SearchViewModel(
    private val suggestionsUseCase: GetSuggestionListUseCase,
    private val historicUseCase: GetHistoricListUseCase) : RxViewModel(), LifecycleObserver {

    private val categoriesState = MutableLiveData<ViewState<List<CategoryScreen>>>()
    private val historicState = MutableLiveData<ViewState<List<String>>>()

    private val isSearchValid = MutableLiveData<Boolean>()

    fun getCategoriesState() = categoriesState.toImmutable()
    fun getHistoricState() = historicState.toImmutable()
    fun getIsSearchValid() = isSearchValid.toImmutable()

    var search = ""
        set(value) {
            field = value
            validateSearch()
        }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchCategories() {
        disposables += suggestionsUseCase.execute()
            .map { CategoryScreenMapper.map(it) }
            .compose(StateMachine())
            .subscribe(
                    { categoriesState.postValue(it) },
                    { Timber.e(it) }
            )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchLastSearches() {
        disposables += historicUseCase.execute()
                .compose(StateMachine())
                .subscribe(
                        { historicState.postValue(it) },
                        { Timber.e(it) }
                )
    }

    private fun validateSearch() {
        isSearchValid.postValue(search.length >= 3)
    }

}