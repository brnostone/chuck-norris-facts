package br.com.stone.core.facts.domain

import io.reactivex.Observable

interface FactsSource {

    fun search(term: String): Observable<List<Fact>>

}