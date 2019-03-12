package br.com.stone.challenge.mock

import android.content.Context
import br.com.stone.data.remote.api.ChuckApi
import com.nhaarman.mockitokotlin2.mock

class MockServer(private val context: Context) {

    private val chuckApi: ChuckApi = mock()

    fun getMock() = chuckApi

    fun simulate(func: ServerSimulator.() -> Unit) = ServerSimulator(context, chuckApi).apply {
        func()
    }

}

