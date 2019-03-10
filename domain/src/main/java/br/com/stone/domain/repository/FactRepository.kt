package br.com.stone.domain.repository

import br.com.stone.domain.Fact
import io.reactivex.Observable

interface FactRepository {

    fun search(query: String): Observable<List<Fact>>

}