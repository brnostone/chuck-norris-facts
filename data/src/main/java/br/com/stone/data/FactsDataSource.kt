package br.com.stone.data

import br.com.stone.domain.Fact
import br.com.stone.domain.FactsSource
import io.reactivex.Observable

class FactsDataSource: FactsSource {

    override fun search(term: String): Observable<List<Fact>> {
        return Observable.empty()
    }

}