package br.com.stone.data.remote

import br.com.stone.data.remote.api.ChuckApi
import br.com.stone.data.remote.mapper.CategoryMapper
import br.com.stone.data.remote.mapper.FactMapper
import br.com.stone.domain.Category
import br.com.stone.domain.Fact
import io.reactivex.Observable

class RemoteService(private val chuckApi: ChuckApi) {

    fun search(query: String): Observable<List<Fact>> {
        return chuckApi.search(query)
            .map { FactMapper.map(it) }
    }

    fun fetchCategories(): Observable<List<Category>> {
        return chuckApi.fetchCategories()
            .map { CategoryMapper.map(it) }
    }

}