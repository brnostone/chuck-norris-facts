package br.com.stone.challenge.feature.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.stone.challenge.feature.common.ViewState
import br.com.stone.domain.exception.NetworkException
import br.com.stone.domain.interactor.GetHistoricListUseCase
import br.com.stone.domain.interactor.GetSuggestionListUseCase
import br.com.stone.test.factory.CategoryFactory
import br.com.stone.test.factory.HistoricFactory
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
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
    private val isSearchValid: Observer<Boolean> = mock()

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
        viewModel.getCategoriesState().observeForever(categoriesState)

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
        viewModel.getCategoriesState().observeForever(categoriesState)

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
        viewModel.getHistoricState().observeForever(lastSearchesState)

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
        viewModel.getHistoricState().observeForever(lastSearchesState)

        val expected = NetworkException.ConnectionException

        whenever(historicUseCase.execute()).thenReturn(Observable.error(expected))
        viewModel.fetchLastSearches()

        inOrder(lastSearchesState) {
            verify(lastSearchesState).onChanged(ViewState.Loading)
            verify(lastSearchesState).onChanged(ViewState.Failed(expected))
        }
    }

    @Test
    fun `should emmit isSearchValid false when search is less than 3 characters`() {
        viewModel.getIsSearchValid().observeForever(isSearchValid)

        val expected = false
        viewModel.search = "a"

        verify(isSearchValid).onChanged(expected)
    }

    @Test
    fun `should emmit isSearchValid true when search is greater than or equal to 3 characters`() {
        viewModel.getIsSearchValid().observeForever(isSearchValid)

        val expected = true
        viewModel.search = "abc"

        verify(isSearchValid).onChanged(expected)
    }

}