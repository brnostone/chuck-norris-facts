package br.com.stone.domain.interactor

import br.com.stone.domain.factory.CategoryFactory
import br.com.stone.domain.repository.CategoryRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class GetSuggestionListUseCaseTest {

    private val categoryRepository: CategoryRepository = mock()
    private lateinit var useCase: GetSuggestionListUseCase

    @Before
    fun beforeEachTest() {
        useCase = GetSuggestionListUseCase(
            categoryRepository = categoryRepository,
            scheduler = Schedulers.trampoline())
    }

    @Test
    fun `should emmit 8 items`() {
        val provided = CategoryFactory.stubList()

        whenever(categoryRepository.fetchAll()).thenReturn(Observable.just(provided))

        val result = useCase.execute().blockingFirst()
        assertTrue("should have 8 items") { result.size == 8 }
    }

    @Test
    fun `should emmit random items`() {
        val provided = CategoryFactory.stubList()

        whenever(categoryRepository.fetchAll()).thenReturn(Observable.just(provided))

        val result = useCase.execute().blockingFirst()
        assertTrue("should have random items") {
            provided.take(8) != result
        }
    }

}