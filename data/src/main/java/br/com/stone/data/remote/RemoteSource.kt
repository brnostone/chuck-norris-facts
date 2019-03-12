package br.com.stone.data.remote

import br.com.stone.domain.Category
import br.com.stone.domain.Fact
import io.reactivex.Observable

interface RemoteSource {

    fun search(query: String): Observable<List<Fact>>
    fun fetchCategories(): Observable<List<Category>>

}