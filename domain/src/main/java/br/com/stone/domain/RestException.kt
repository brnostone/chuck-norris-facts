package br.com.stone.domain

sealed class RestException(val code: Int): Exception() {

    class ClientError(code: Int): RestException(code)
    class ServerError(code: Int): RestException(code)

}