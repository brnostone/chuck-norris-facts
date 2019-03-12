package br.com.stone.challenge.feature.base

import android.app.Activity
import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import br.com.stone.challenge.mock.MockServer
import org.junit.Before
import org.junit.Rule
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import kotlin.reflect.KClass

open class BaseTest<T: Activity> (klass: KClass<T>) {

    protected val mockServer = MockServer(InstrumentationRegistry.getInstrumentation().context)

    @get:Rule
    val activityRule = ActivityTestRule(klass.java, true, false)

    @Before
    fun init() {
        val mockModule = module {
            factory(override = true) {
                mockServer.getMock()
            }
        }

        loadKoinModules(mockModule)
    }

    protected fun startActivity() {
        activityRule.launchActivity(Intent())
    }

}