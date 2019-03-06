package br.com.stone.data.remote.api

import br.com.stone.data.remote.model.FactPayload
import br.com.stone.data.remote.model.ResultsPayload
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ChuckApi {

    @GET("jokes/search")
    fun search(@Query("query") query: String): Observable<ResultsPayload<FactPayload>>

    companion object {
        const val API_URL = "https://api.chucknorris.io/"
    }

}