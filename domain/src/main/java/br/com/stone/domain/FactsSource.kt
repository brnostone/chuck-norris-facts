package br.com.stone.domain

import io.reactivex.Observable

interface FactsSource {

    fun search(term: String): Observable<List<Fact>>

}