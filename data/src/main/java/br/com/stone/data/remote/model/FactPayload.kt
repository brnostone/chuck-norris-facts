package br.com.stone.data.remote.model

import com.google.gson.annotations.SerializedName

class FactPayload {

    @SerializedName("url")
    lateinit var url: String

    @SerializedName("value")
    lateinit var text: String

    @SerializedName("category")
    var categories: List<String>? = null

}