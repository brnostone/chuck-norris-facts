package br.com.stone.data.local

import br.com.stone.domain.Category
import io.reactivex.Completable
import io.reactivex.Single

interface CacheService {

    fun fetchAll(): Single<List<Category>>
    fun save(items: List<Category>): Completable

    fun clear(): Completable

}