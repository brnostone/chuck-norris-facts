package br.com.stone.data

import br.com.stone.data.local.CacheService
import br.com.stone.data.remote.RemoteService
import br.com.stone.domain.Fact
import br.com.stone.domain.FactsSource
import io.reactivex.Observable

class FactsDataSource(
    private val remoteService: RemoteService,
    private val cacheService: CacheService): FactsSource {

    override fun search(query: String): Observable<List<Fact>> {
        return remoteService.search(query)
    }

}