package br.com.stone.data.remote

import br.com.stone.data.remote.api.ChuckApi
import br.com.stone.data.factory.DataFactory
import br.com.stone.data.util.FilesFromTestResources
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RemoteServiceTest {

    private lateinit var server: MockWebServer
    private lateinit var remoteService: RemoteService

    @Before
    fun beforeEachTest() {
        server = MockWebServer()

        val chuckApi = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(server.url("/").toString())
            .build()
            .create(ChuckApi::class.java)

        remoteService = RemoteService(chuckApi)
    }

    @After
    fun afterEachTest() {
        server.shutdown()
    }

    @Test
    fun `should retrieve facts correctly`() {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(FilesFromTestResources.getJson("remote/facts_search_correct"))
        )

        remoteService.search(DataFactory.randomString())
            .test()
            .assertNoErrors()
            .assertComplete()
    }


}