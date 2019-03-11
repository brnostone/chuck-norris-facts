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
    val historicState = MutableLiveData<ViewState<List<String>>>()

    val isSearchValid = MutableLiveData<Boolean>()

    var search = ""
        set(value) {
            field = value
            validateSearch()
        }

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
                        { historicState.postValue(it) },
                        { Timber.e(it) }
                )
    }

    private fun validateSearch() {
        isSearchValid.postValue(search.length >= 3)
    }

}