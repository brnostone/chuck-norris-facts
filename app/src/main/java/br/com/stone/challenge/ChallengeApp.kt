package br.com.stone.challenge

import android.app.Application
import br.com.stone.challenge.di.presentationModule
import br.com.stone.data.di.dataModules
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class ChallengeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return String.format("[%s] (%s:%s)",
                        super.createStackElementTag(element),
                        element.methodName,
                        element.lineNumber)
                }
            })
        }

        startKoin(this, dataModules + listOf(presentationModule))
    }

}