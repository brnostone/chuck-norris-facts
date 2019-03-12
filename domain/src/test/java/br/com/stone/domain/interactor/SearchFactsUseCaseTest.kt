package br.com.stone.domain.interactor

import br.com.stone.domain.repository.FactRepository
import br.com.stone.domain.repository.HistoricRepository
import br.com.stone.test.factory.FactFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class SearchFactsUseCaseTest {

    private val factRepository: FactRepository = mock()
    private val historicRepository: HistoricRepository = mock()
    private lateinit var useCase: SearchFactsUseCase

    @Before
    fun beforeEachTest() {
        useCase = SearchFactsUseCase(
            factRepository = factRepository,
            historicRepository = historicRepository,
            scheduler = Schedulers.trampoline())
    }

    @Test
    fun `should emmit all items`() {
        val searchTem = "term"

        val provided = FactFactory.stubList()
        val expected = provided

        whenever(factRepository.search(any())).thenReturn(Observable.just(provided))
        whenever(historicRepository.insert(any())).thenReturn(Completable.complete())

        useCase.execute(searchTem)
            .test()
            .assertResult(expected)
    }

    @Test
    fun `should save the search term`() {
        val searchTem = "term"
        val provided = FactFactory.stubList()

        whenever(factRepository.search(any())).thenReturn(Observable.just(provided))
        whenever(historicRepository.insert(any())).thenReturn(Completable.complete())

        useCase.execute(searchTem)
            .test()
            .assertResult(provided)

        verify(historicRepository).insert(searchTem)
    }

}