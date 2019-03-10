package br.com.stone.data.local

import android.annotation.SuppressLint
import android.content.Context
import io.reactivex.Completable
import io.reactivex.Observable

@SuppressLint("ApplySharedPref")
class HistoricLocalDataSource(context: Context) : HistoricLocalSource {

    companion object {
        private const val HISTORIC_KEY = "historic"
    }

    private val sharedPref by lazy {
        context.getSharedPreferences("historic.cache", Context.MODE_PRIVATE)
    }

    override fun fetchAll(): Observable<List<String>> {
        return Observable.fromCallable {
            sharedPref
                .getString(HISTORIC_KEY, null)
                ?.split("#") ?: emptyList()
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
            val editor = sharedPref.edit()

            editor.putString(HISTORIC_KEY, historic.joinToString("#"))

            editor.commit()
        }
    }
}