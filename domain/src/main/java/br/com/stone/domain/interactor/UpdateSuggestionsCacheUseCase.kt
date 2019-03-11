package br.com.stone.domain.interactor

import br.com.stone.domain.repository.CategoryRepository
import io.reactivex.Completable
import io.reactivex.Scheduler

class UpdateSuggestionsCacheUseCase(
        private val categoryRepository: CategoryRepository,
        private val scheduler: Scheduler) {

    fun execute(): Completable {
        return categoryRepository.updateCache()
                .subscribeOn(scheduler)
    }

}