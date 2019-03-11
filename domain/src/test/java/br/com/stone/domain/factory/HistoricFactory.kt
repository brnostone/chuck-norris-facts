package br.com.stone.domain.factory

object HistoricFactory {

    fun stubList(): List<String> =
            (1..20)
                    .map { it * it }
                    .map { it.toString() }
                    .map { "Historic ${it[0]}" }

}