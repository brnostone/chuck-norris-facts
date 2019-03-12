package br.com.stone.data.local

import android.annotation.SuppressLint
import android.content.Context
import br.com.stone.domain.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Completable
import io.reactivex.Observable

@SuppressLint("ApplySharedPref")
class CategoryLocalDataSource(context: Context) : CategoryLocalSource {

    private val gson by lazy { Gson() }

    companion object {
        private const val CATEGORIES_KEY = "categories"
    }

    private val sharedPref by lazy {
        context.getSharedPreferences("category.cache", Context.MODE_PRIVATE)
    }

    override fun fetchAll(): Observable<List<Category>> {
        return Observable.fromCallable {
            val savedData = sharedPref.getString(CATEGORIES_KEY, "[]")
            val parsedList = gson.fromJson<List<String>>(savedData, object : TypeToken<List<String>>() {}.type)

            parsedList.map { Category(it) }
        }
    }

    override fun save(items: List<Category>): Completable {
        return Completable.fromCallable {
            val saveData = gson.toJson(items.map { it.name })

            sharedPref.edit()
                .putString(CATEGORIES_KEY, saveData)
                .commit()
        }
    }

}