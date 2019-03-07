package br.com.stone.domain

sealed class NetworkException: Exception() {

    object TimeoutException: NetworkException()
    object ConnectionException: NetworkException()

}