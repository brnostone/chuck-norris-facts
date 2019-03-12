package br.com.stone.data

import br.com.stone.data.remote.RemoteSource
import br.com.stone.test.factory.DataFactory
import br.com.stone.test.factory.FactFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class FactDataRepositoryTest {

    private val remoteSource: RemoteSource = mock()

    private lateinit var repository: FactDataRepository

    @Before
    fun beforeEachTest() {
        repository = FactDataRepository(
            remoteSource = remoteSource
        )
    }

    @Test
    fun `should fetch remote items`() {
        val searchTerm = DataFactory.randomString()

        val provided = FactFactory.stubList()
        val expected = provided

        whenever(remoteSource.search(searchTerm)).thenReturn(Observable.just(provided))

        repository.search(searchTerm)
            .test()
            .assertResult(expected)
    }

}