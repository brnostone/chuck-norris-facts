package br.com.stone.data.remote.model

import com.google.gson.annotations.SerializedName

class ResultsPayload<T> {

    @SerializedName("result")
    lateinit var results: List<T>

}