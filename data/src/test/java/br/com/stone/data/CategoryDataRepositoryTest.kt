package br.com.stone.data

import br.com.stone.data.local.CategoryLocalSource
import br.com.stone.data.remote.RemoteSource
import br.com.stone.test.factory.CategoryFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
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

    @Test
    fun `should fetch remote items and save in cache`() {
        val provided = CategoryFactory.stubList()
        val expected = provided

        whenever(categoryLocalSource.fetchAll()).thenReturn(Observable.just(emptyList()))
        whenever(remoteSource.fetchCategories()).thenReturn(Observable.just(provided))
        whenever(categoryLocalSource.save(any())).thenReturn(Completable.complete())

        repository.fetchAll()
            .test()
            .assertResult(expected)

        verify(categoryLocalSource).save(expected)
    }

    @Test
    fun `should update cache`() {
        val provided = CategoryFactory.stubList()
        val expected = provided

        whenever(remoteSource.fetchCategories()).thenReturn(Observable.just(provided))
        whenever(categoryLocalSource.save(any())).thenReturn(Completable.complete())

        repository.updateCache()
            .test()
            .assertResult()

        verify(categoryLocalSource).save(expected)
    }

}