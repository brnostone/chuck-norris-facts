package br.com.stone.data.local

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Completable
import io.reactivex.Observable

@SuppressLint("ApplySharedPref")
class HistoricLocalDataSource(context: Context) : HistoricLocalSource {

    private val gson by lazy { Gson() }

    companion object {
        private const val HISTORIC_KEY = "historic"
    }

    private val sharedPref by lazy {
        context.getSharedPreferences("historic.cache", Context.MODE_PRIVATE)
    }

    override fun fetchAll(): Observable<List<String>> {
        return Observable.fromCallable {
            val savedData = sharedPref.getString(HISTORIC_KEY, "[]")
            gson.fromJson<List<String>>(savedData, object : TypeToken<List<String>>() {}.type)
        }
    }

    override fun put(term: String): Completable {
        return fetchAll()
            .flatMapCompletable { historic ->
                val data = historic.toMutableList().apply {
                    add(0, term)
                }

                saveHistoric(data)
            }
    }

    private fun saveHistoric(historic: List<String>) : Completable {
        return Completable.fromCallable {
            val saveData = gson.toJson(historic)

            sharedPref.edit()
                .putString(HISTORIC_KEY, saveData)
                .commit()
        }
    }

}