package br.com.stone.data

import br.com.stone.data.remote.RemoteDataSource
import br.com.stone.data.remote.networkerrors.MapNetworkErrors
import br.com.stone.domain.Fact
import br.com.stone.domain.repository.FactRepository
import io.reactivex.Observable

class FactDataRepository(private val remoteDataSource: RemoteDataSource): FactRepository {

    override fun search(query: String): Observable<List<Fact>> {
        return remoteDataSource.search(query)
            .compose(MapNetworkErrors())
    }

}