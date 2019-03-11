package br.com.stone.data

import br.com.stone.data.local.CategoryLocalSource
import br.com.stone.data.remote.RemoteDataSource
import br.com.stone.data.remote.networkerrors.MapNetworkErrors
import br.com.stone.domain.Category
import br.com.stone.domain.repository.CategoryRepository
import io.reactivex.Completable
import io.reactivex.Observable

class CategoryDataRepository(
    private val categoryLocalSource: CategoryLocalSource,
    private val remoteDataSource: RemoteDataSource): CategoryRepository {

    override fun fetchAll(): Observable<List<Category>> {
        return categoryLocalSource.fetchAll()
            .switchMap { categories ->
                if (categories.isEmpty())
                    fetchRemoteCategoriesAndSave()
                else
                    Observable.just(categories)
            }
            .compose(MapNetworkErrors())
    }

    override fun updateCache(): Completable {
        return fetchRemoteCategoriesAndSave()
                .ignoreElements()
    }

    private fun fetchRemoteCategoriesAndSave(): Observable<List<Category>> {
        return remoteDataSource.fetchCategories()
            .flatMapSingle { categoryLocalSource.save(it).toSingleDefault(it) }
    }

}