package br.com.stone.challenge.feature.search

import androidx.lifecycle.MutableLiveData
import br.com.stone.challenge.feature.common.StateMachine
import br.com.stone.challenge.feature.common.ViewState
import br.com.stone.challenge.util.RxViewModel
import br.com.stone.domain.interactor.GetHistoricListUseCase
import br.com.stone.domain.interactor.GetSuggestionListUseCase
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber

class SearchViewModel(
    private val suggestionsUseCase: GetSuggestionListUseCase,
    private val historicUseCase: GetHistoricListUseCase) : RxViewModel() {

    val categoriesState = MutableLiveData<ViewState<List<CategoryScreen>>>()
    val lastSearchesState = MutableLiveData<ViewState<List<String>>>()

    fun fetchCategories() {
        disposables += suggestionsUseCase.execute()
            .map { CategoryScreenMapper.map(it) }
            .compose(StateMachine())
            .subscribe(
                    { categoriesState.postValue(it) },
                    { Timber.e(it) }
            )
    }

    fun fetchLastSearches() {
        disposables += historicUseCase.execute()
                .compose(StateMachine())
                .subscribe(
                        { lastSearchesState.postValue(it) },
                        { Timber.e(it) }
                )
    }

}