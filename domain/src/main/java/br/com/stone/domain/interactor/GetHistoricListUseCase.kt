package br.com.stone.domain.interactor

import br.com.stone.domain.repository.HistoricRepository
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetHistoricListUseCase(
    private val historicRepository: HistoricRepository,
    private val scheduler: Scheduler) {

    fun execute(): Observable<List<String>> {
        return historicRepository.fetchAll()
            .map { it.distinct() }
            .subscribeOn(scheduler)
    }

}