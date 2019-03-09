package br.com.stone.data.local

import android.annotation.SuppressLint
import android.content.Context
import br.com.stone.domain.Category
import io.reactivex.Completable
import io.reactivex.Single

@SuppressLint("ApplySharedPref")
class PreferenceService(context: Context): CacheService {

    companion object {
        private const val CATEGORIES_KEY = "categories"
    }

    private val sharedPref by lazy {
        context.getSharedPreferences("cache", Context.MODE_PRIVATE)
    }

    override fun fetchAll(): Single<List<Category>> {
        return Single.fromCallable {
            sharedPref
                .getString(CATEGORIES_KEY, null)
                ?.split("#")
                ?.map { Category(it) } ?: emptyList()
        }
    }

    override fun save(items: List<Category>): Completable {
        return Completable.fromCallable {
            val editor = sharedPref.edit()

            editor.putString(CATEGORIES_KEY, items.joinToString("#") { it.name })

            editor.commit()
        }
    }

    override fun clear(): Completable {
        return Completable.fromCallable {
            sharedPref.edit().clear()
        }
    }

}