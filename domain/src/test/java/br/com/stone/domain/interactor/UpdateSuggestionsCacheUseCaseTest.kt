package br.com.stone.domain.interactor

import br.com.stone.domain.repository.CategoryRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class UpdateSuggestionsCacheUseCaseTest {

    private val categoryRepository: CategoryRepository = mock()
    private lateinit var useCase: UpdateSuggestionsCacheUseCase

    @Before
    fun beforeEachTest() {
        useCase = UpdateSuggestionsCacheUseCase(
                categoryRepository = categoryRepository,
                scheduler = Schedulers.trampoline())
    }

    @Test
    fun `should call updateCache`() {
        whenever(categoryRepository.updateCache()).thenReturn(Completable.complete())

        useCase.execute().blockingAwait()
        verify(categoryRepository).updateCache()
    }

}