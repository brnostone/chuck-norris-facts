package br.com.stone.data

import br.com.stone.data.local.HistoricLocalSource
import br.com.stone.data.remote.networkerrors.MapNetworkErrors
import br.com.stone.domain.repository.HistoricRepository
import io.reactivex.Completable
import io.reactivex.Observable

class HistoricDataRepository(private val historicLocalSource: HistoricLocalSource): HistoricRepository {

    override fun fetchAll(): Observable<List<String>> {
        return historicLocalSource.fetchAll()
            .compose(MapNetworkErrors())
    }

    override fun insert(term: String): Completable {
        return historicLocalSource.put(term)
    }

}