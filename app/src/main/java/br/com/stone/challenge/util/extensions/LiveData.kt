package br.com.stone.challenge.util.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.toImmutable(): LiveData<T> = this