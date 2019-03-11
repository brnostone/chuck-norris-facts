package br.com.stone.domain.interactor

import br.com.stone.domain.Fact
import br.com.stone.domain.exception.TermSizeException
import br.com.stone.domain.repository.FactRepository
import br.com.stone.domain.repository.HistoricRepository
import io.reactivex.Observable
import io.reactivex.Scheduler

class SearchFactsUseCase(
    private val factRepository: FactRepository,
    private val historicRepository: HistoricRepository,
    private val scheduler: Scheduler) {

    fun execute(term: String): Observable<List<Fact>> {
        if (term.length < 3)
            return Observable.error(TermSizeException())

        return historicRepository.insert(term)
            .andThen(factRepository.search(term))
            .subscribeOn(scheduler)
    }

}