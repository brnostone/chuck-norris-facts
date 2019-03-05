package br.com.stone.challenge

import android.app.Application
import br.com.stone.challenge.di.presentationModule
import br.com.stone.data.di.dataModule
import org.koin.android.ext.android.startKoin

class ChallengeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(
            presentationModule,
            dataModule
        ))
    }

}