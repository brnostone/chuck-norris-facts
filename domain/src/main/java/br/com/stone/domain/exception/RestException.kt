package br.com.stone.domain.exception

sealed class RestException(val code: Int): Exception() {

    class ClientError(code: Int): RestException(code)
    class ServerError(code: Int): RestException(code)

    override fun toString() = "RestException - code: $code"

}