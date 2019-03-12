package br.com.stone.data.remote

import br.com.stone.data.remote.api.ChuckApi
import br.com.stone.data.util.FilesFromTestResources
import br.com.stone.test.factory.DataFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSourceTest {

    private lateinit var server: MockWebServer
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun beforeEachTest() {
        server = MockWebServer()

        val chuckApi = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(server.url("/").toString())
            .build()
            .create(ChuckApi::class.java)

        remoteDataSource = RemoteDataSource(chuckApi)
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

        remoteDataSource.search(DataFactory.randomString())
            .test()
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `should retrieve categories correctly`() {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(FilesFromTestResources.getJson("remote/categories_correct"))
        )

        remoteDataSource.fetchCategories()
            .test()
            .assertNoErrors()
            .assertComplete()
    }


}