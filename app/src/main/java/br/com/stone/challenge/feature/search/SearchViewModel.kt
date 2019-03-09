package br.com.stone.challenge.feature.search

import androidx.lifecycle.MutableLiveData
import br.com.stone.challenge.feature.common.StateMachine
import br.com.stone.challenge.feature.common.ViewState
import br.com.stone.challenge.util.RxViewModel
import br.com.stone.domain.FactsSource
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber

class SearchViewModel(private val factsSource: FactsSource) : RxViewModel() {

    val categoriesState = MutableLiveData<ViewState<List<CategoryScreen>>>()

    fun fetchCategories() {
        disposables += factsSource.fetchCategories()
            .map { CategoryScreenMapper.map(it) }
            .compose(StateMachine())
            .subscribe(
                { categoriesState.postValue(it) },
                { Timber.e(it) }
            )
    }

}