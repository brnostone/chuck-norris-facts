package br.com.stone.data.local

import br.com.stone.domain.Category
import io.reactivex.Completable
import io.reactivex.Observable

interface CacheService {

    fun fetchCategories(): Observable<List<Category>>
    fun fetchLastSearches(): Observable<List<String>>

    fun save(items: List<Category>): Completable

    fun put(term: String): Completable

    fun clear(): Completable

}