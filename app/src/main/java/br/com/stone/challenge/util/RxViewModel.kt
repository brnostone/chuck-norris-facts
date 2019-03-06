package br.com.stone.challenge.util

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class RxViewModel: ViewModel() {

    val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}