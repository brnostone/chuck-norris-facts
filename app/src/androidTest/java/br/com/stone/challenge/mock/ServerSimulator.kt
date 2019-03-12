package br.com.stone.challenge.mock

import android.content.Context
import br.com.stone.data.remote.api.ChuckApi
import br.com.stone.data.remote.model.CategoryPayload
import br.com.stone.data.remote.model.FactPayload
import br.com.stone.data.remote.model.ResultsPayload
import br.com.stone.domain.exception.NetworkException
import br.com.stone.domain.exception.RestException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable

class ServerSimulator(private val context: Context, private val chuckApi: ChuckApi) {
    private val gson = Gson()

    fun factsCorrect() {
        val payload = generatePayload<ResultsPayload<FactPayload>>("remote/facts_search_correct")
        whenever(chuckApi.search(any())).thenReturn(Observable.just(payload))
    }

    fun categoriesCorrect() {
        val payload = generatePayload<List<CategoryPayload>>("remote/categories_correct")
        whenever(chuckApi.fetchCategories()).thenReturn(Observable.just(payload))
    }

    fun factsNetworkError() {
        whenever(chuckApi.search(any())).thenReturn(Observable.error(NetworkException.ConnectionException))
    }

    fun factsServerError() {
        whenever(chuckApi.search(any())).thenReturn(Observable.error(RestException.ServerError(502)))
    }

    fun factsGenericError() {
        whenever(chuckApi.search(any())).thenReturn(Observable.error(NullPointerException()))
    }


    fun allCorrect() {
        factsCorrect()
        categoriesCorrect()
    }


    private fun getJson(path : String) : String {
        val inputStream = context.assets.open("json/$path.json")
        return String(inputStream.readBytes())
    }

    private inline fun <reified T> generatePayload(mock: String): T {
        val data = getJson(mock)
        return gson.fromJson(data, object : TypeToken<T>() {}.type)
    }

}