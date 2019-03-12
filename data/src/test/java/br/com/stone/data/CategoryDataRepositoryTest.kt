package br.com.stone.data

import br.com.stone.data.local.CategoryLocalSource
import br.com.stone.data.remote.RemoteSource
import br.com.stone.test.factory.CategoryFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class CategoryDataRepositoryTest {

    private val categoryLocalSource: CategoryLocalSource = mock()
    private val remoteSource: RemoteSource = mock()

    private lateinit var repository: CategoryDataRepository

    @Before
    fun beforeEachTest() {
        repository = CategoryDataRepository(
            categoryLocalSource = categoryLocalSource,
            remoteSource = remoteSource
        )
    }

    @Test
    fun `should emmit cache items`() {
        val provided = CategoryFactory.stubList()
        val expected = provided

        whenever(categoryLocalSource.fetchAll()).thenReturn(Observable.just(provided))
        whenever(remoteSource.fetchCategories()).thenReturn(Observable.just(listOf(CategoryFactory.stub())))

        repository.fetchAll()
            .test()
            .assertResult(expected)
    }

}