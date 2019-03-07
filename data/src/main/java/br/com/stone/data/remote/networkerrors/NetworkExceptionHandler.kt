package br.com.stone.data.remote.networkerrors

import br.com.stone.domain.NetworkException
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class NetworkExceptionHandler<T> : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream
            .onErrorResumeNext { throwable: Throwable ->
                Observable.error(mapError(throwable))
            }
    }

    private fun mapError(throwable: Throwable) = when {
        isTimeoutError(throwable) -> NetworkException.TimeoutException
        isConnectionError(throwable) -> NetworkException.ConnectionException
        isRequestCanceled(throwable) -> NetworkException.RequestCanceledException
        else -> throwable
    }

    private fun isTimeoutError(throwable: Throwable): Boolean {
        return throwable is SocketTimeoutException
    }

    private fun isConnectionError(throwable: Throwable): Boolean {
        return throwable is ConnectException || throwable is UnknownHostException
    }

    private fun isRequestCanceled(throwable: Throwable): Boolean {
        return throwable is IOException && throwable.message?.contentEquals("Canceled") ?: false
    }

}
