package br.com.stone.domain

import io.reactivex.Observable

interface FactsSource {

    fun search(query: String): Observable<List<Fact>>

    fun fetchCategories(): Observable<List<Category>>

}