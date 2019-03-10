package br.com.stone.data.local

import br.com.stone.domain.Category
import io.reactivex.Completable
import io.reactivex.Observable

interface CategoryLocalSource {

    fun fetchAll(): Observable<List<Category>>

    fun save(items: List<Category>): Completable

}