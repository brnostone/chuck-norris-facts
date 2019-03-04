package br.com.stone.challenge

import android.app.Application
import br.com.stone.core.di.coreModule
import org.koin.android.ext.android.startKoin

class ChallengeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(
            coreModule
        ))
    }

}