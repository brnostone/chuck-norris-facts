package br.com.stone.data.local

import io.reactivex.Completable
import io.reactivex.Observable

interface HistoricLocalSource {

    fun fetchAll(): Observable<List<String>>

    fun put(term: String): Completable

}