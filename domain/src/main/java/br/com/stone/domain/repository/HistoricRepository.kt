package br.com.stone.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable

interface HistoricRepository {

    fun fetchAll(): Observable<List<String>>

    fun insert(term: String): Completable

}