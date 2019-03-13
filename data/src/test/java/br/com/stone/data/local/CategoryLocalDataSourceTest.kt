package br.com.stone.data.local

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import br.com.stone.test.factory.CategoryFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CategoryLocalDataSourceTest {

    private lateinit var categoryLocalSource: CategoryLocalDataSource
    private val context by lazy { ApplicationProvider.getApplicationContext<Context>() }

    @Before
    fun beforeEachTest() {
        categoryLocalSource = CategoryLocalDataSource(context)
    }

    @Test
    fun `should save items correctly`() {
        val provided = CategoryFactory.stubList()
        val expected = provided

        categoryLocalSource.save(provided).blockingAwait()
        categoryLocalSource.fetchAll()
            .test()
            .assertResult(expected)
    }

}