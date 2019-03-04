package br.com.stone.core.facts.infrastructure

import br.com.stone.core.facts.domain.Fact
import br.com.stone.core.facts.domain.FactsSource
import io.reactivex.Observable

class FactsInfrastructure: FactsSource {

    override fun search(term: String): Observable<List<Fact>> {
        return Observable.empty()
    }

}