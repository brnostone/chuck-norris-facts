package br.com.stone.challenge.feature.facts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.stone.challenge.feature.common.ViewState
import br.com.stone.challenge.feature.factory.FactFactory
import br.com.stone.domain.repository.FactRepository
import br.com.stone.domain.exception.NetworkException
import br.com.stone.domain.interactor.SearchFactsUseCase
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FactsViewModelTest {

    private val searchUseCase: SearchFactsUseCase = mock()
    lateinit var viewModel: FactsViewModel

    private val state: Observer<ViewState<List<FactScreen>>> = mock()
    private val fact = FactFactory.create()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun beforeEachTest() {
        viewModel = FactsViewModel(searchUseCase)
    }

    @Test
    fun `should emmit states of success`() {
        viewModel.state.observeForever(state)

        val provided = listOf(fact)
        val expected = FactScreenMapper.map(provided)

        whenever(searchUseCase.execute(any())).thenReturn(Observable.just(provided))
        viewModel.search("dev")

        inOrder(state) {
            verify(state).onChanged(ViewState.Default)
            verify(state).onChanged(ViewState.Loading)
            verify(state).onChanged(ViewState.Success(expected))
        }
    }


    @Test
    fun `should emmit states of error`() {
        viewModel.state.observeForever(state)

        val expected = NetworkException.ConnectionException

        whenever(searchUseCase.execute(any())).thenReturn(Observable.error(expected))
        viewModel.search("dev")

        inOrder(state) {
            verify(state).onChanged(ViewState.Default)
            verify(state).onChanged(ViewState.Loading)
            verify(state).onChanged(ViewState.Failed(expected))
        }
    }

}