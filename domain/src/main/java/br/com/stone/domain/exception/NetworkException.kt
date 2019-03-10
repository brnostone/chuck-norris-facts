package br.com.stone.domain.exception

sealed class NetworkException: Exception() {

    object TimeoutException: NetworkException()
    object ConnectionException: NetworkException()

}