package br.com.stone.data.local

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HistoricLocalDataSourceTest {

    private lateinit var historicLocalSource: HistoricLocalDataSource
    private val context by lazy { ApplicationProvider.getApplicationContext<Context>() }

    @Before
    fun beforeEachTest() {
        historicLocalSource = HistoricLocalDataSource(context)
    }

    @Test
    fun `should save the items in the right order`() {
        val provided = (1..10).map { "Historic $it" }
        val expected = provided.asReversed()

        Observable.fromIterable(provided)
            .flatMapCompletable { historicLocalSource.put(it) }
            .blockingAwait()


        historicLocalSource.fetchAll()
            .test()
            .assertResult(expected)
    }

}