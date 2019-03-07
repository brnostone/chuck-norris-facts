package br.com.stone.data.remote.networkerrors

import br.com.stone.domain.RestException
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import retrofit2.HttpException

class HttpExceptionHandler<T> : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream
            .onErrorResumeNext { throwable: Throwable ->
                Observable.error(mapError(throwable))
            }
    }

    private fun mapError(throwable: Throwable): Throwable {
        if (throwable !is HttpException)
            return throwable

        return when (throwable.code()) {
            in 400 until 500 -> RestException.ClientError(throwable.code())
            in 500 until 600 -> RestException.ServerError(throwable.code())
            else -> throwable
        }
    }

}