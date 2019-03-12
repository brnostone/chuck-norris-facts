package br.com.stone.data

import br.com.stone.data.remote.RemoteSource
import br.com.stone.data.remote.networkerrors.MapNetworkErrors
import br.com.stone.domain.Fact
import br.com.stone.domain.repository.FactRepository
import io.reactivex.Observable

class FactDataRepository(private val remoteSource: RemoteSource): FactRepository {

    override fun search(query: String): Observable<List<Fact>> {
        return remoteSource.search(query)
            .compose(MapNetworkErrors())
    }

}