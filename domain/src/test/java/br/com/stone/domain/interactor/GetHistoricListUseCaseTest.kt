package br.com.stone.domain.interactor

import br.com.stone.domain.repository.HistoricRepository
import br.com.stone.test.factory.HistoricFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class GetHistoricListUseCaseTest {

    private val historicRepository: HistoricRepository = mock()
    private lateinit var useCase: GetHistoricListUseCase

    @Before
    fun beforeEachTest() {
        useCase = GetHistoricListUseCase(
            historicRepository = historicRepository,
            scheduler = Schedulers.trampoline())
    }

    @Test
    fun `should emmit distinct items`() {
        val provided = HistoricFactory.stubList()
        val expected = provided.distinct()

        whenever(historicRepository.fetchAll()).thenReturn(Observable.just(provided))

        useCase.execute()
            .test()
            .assertResult(expected)
    }

}