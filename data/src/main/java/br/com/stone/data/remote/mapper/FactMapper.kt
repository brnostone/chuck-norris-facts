package br.com.stone.data.remote.mapper

import br.com.stone.data.remote.model.FactPayload
import br.com.stone.data.remote.model.ResultsPayload
import br.com.stone.domain.Fact

object FactMapper {

    fun map(payload: FactPayload): Fact {
        val categories = payload.categories?.let { CategoryMapper.map(it) }

        return Fact(
            url = payload.url,
            text = payload.text,
            categories = categories ?: emptyList()
        )
    }

    fun map(payload: ResultsPayload<FactPayload>) = payload.results.map { map(it) }

}