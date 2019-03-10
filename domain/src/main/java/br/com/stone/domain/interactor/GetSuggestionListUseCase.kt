package br.com.stone.domain.interactor

import br.com.stone.domain.Category
import br.com.stone.domain.repository.CategoryRepository
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetSuggestionListUseCase(
    private val categoryRepository: CategoryRepository,
    private val scheduler: Scheduler) {

    fun execute(): Observable<List<Category>> {
        return categoryRepository.fetchAll()
            .map { categories ->
                categories.shuffled()
                .take(8)
            }
            .subscribeOn(scheduler)
    }

}