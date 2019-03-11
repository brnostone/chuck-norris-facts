package br.com.stone.domain.repository

import br.com.stone.domain.Category
import io.reactivex.Completable
import io.reactivex.Observable

interface CategoryRepository {

    fun fetchAll(): Observable<List<Category>>
    fun updateCache(): Completable

}