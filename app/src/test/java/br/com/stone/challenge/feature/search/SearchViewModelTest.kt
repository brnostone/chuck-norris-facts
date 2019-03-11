package br.com.stone.challenge.feature.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.stone.challenge.feature.common.ViewState
import br.com.stone.domain.factory.CategoryFactory
import br.com.stone.domain.factory.HistoricFactory
import br.com.stone.domain.exception.NetworkException
import br.com.stone.domain.interactor.GetHistoricListUseCase
import br.com.stone.domain.interactor.GetSuggestionListUseCase
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    private val suggestionsUseCase: GetSuggestionListUseCase = mock()
    private val historicUseCase: GetHistoricListUseCase = mock()
    private lateinit var viewModel: SearchViewModel

    private val categoriesState: Observer<ViewState<List<CategoryScreen>>> = mock()
    private val lastSearchesState: Observer<ViewState<List<String>>> = mock()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun beforeEachTest() {
        viewModel = SearchViewModel(
                suggestionsUseCase = suggestionsUseCase,
                historicUseCase = historicUseCase
        )
    }

    @Test
    fun `should emmit states of suggestions success`() {
        viewModel.categoriesState.observeForever(categoriesState)

        val provided = CategoryFactory.stubList()
        val expected = CategoryScreenMapper.map(provided)

        whenever(suggestionsUseCase.execute()).thenReturn(Observable.just(provided))
        viewModel.fetchCategories()

        inOrder(categoriesState) {
            verify(categoriesState).onChanged(ViewState.Loading)
            verify(categoriesState).onChanged(ViewState.Success(expected))
        }
    }

    @Test
    fun `should emmit states of suggestions error`() {
        viewModel.categoriesState.observeForever(categoriesState)

        val expected = NetworkException.ConnectionException
        whenever(suggestionsUseCase.execute()).thenReturn(Observable.error(expected))
        viewModel.fetchCategories()

        inOrder(categoriesState) {
            verify(categoriesState).onChanged(ViewState.Loading)
            verify(categoriesState).onChanged(ViewState.Failed(expected))
        }
    }

    @Test
    fun `should emmit states of historic success`() {
        viewModel.lastSearchesState.observeForever(lastSearchesState)

        val provided = HistoricFactory.stubList()
        val expected = HistoricFactory.stubList()

        whenever(historicUseCase.execute()).thenReturn(Observable.just(provided))
        viewModel.fetchLastSearches()

        inOrder(lastSearchesState) {
            verify(lastSearchesState).onChanged(ViewState.Loading)
            verify(lastSearchesState).onChanged(ViewState.Success(expected))
        }
    }

    @Test
    fun `should emmit states of historic error`() {
        viewModel.lastSearchesState.observeForever(lastSearchesState)

        val expected = NetworkException.ConnectionException

        whenever(historicUseCase.execute()).thenReturn(Observable.error(expected))
        viewModel.fetchLastSearches()

        inOrder(lastSearchesState) {
            verify(lastSearchesState).onChanged(ViewState.Loading)
            verify(lastSearchesState).onChanged(ViewState.Failed(expected))
        }
    }

}