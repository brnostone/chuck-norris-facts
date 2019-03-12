package br.com.stone.data

import br.com.stone.data.local.HistoricLocalSource
import br.com.stone.test.factory.DataFactory
import br.com.stone.test.factory.HistoricFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class HistoricDataRepositoryTest {

    private val historicLocalSource: HistoricLocalSource = mock()

    private lateinit var repository: HistoricDataRepository

    @Before
    fun beforeEachTest() {
        repository = HistoricDataRepository(
            historicLocalSource = historicLocalSource
        )
    }

    @Test
    fun `should fetch local items`() {
        val provided = HistoricFactory.stubList()
        val expected = provided

        whenever(historicLocalSource.fetchAll()).thenReturn(Observable.just(provided))

        repository.fetchAll()
            .test()
            .assertResult(expected)
    }

    @Test
    fun `should save local item`() {
        val provided = DataFactory.randomString()
        val expected = provided

        whenever(historicLocalSource.put(provided)).thenReturn(Completable.complete())

        repository.insert(provided)
            .test()
            .assertResult()

        verify(historicLocalSource).put(expected)
    }

}