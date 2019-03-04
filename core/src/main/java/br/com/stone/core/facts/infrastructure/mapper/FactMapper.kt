package br.com.stone.core.facts.infrastructure.mapper

import br.com.stone.core.facts.domain.Fact
import br.com.stone.core.facts.infrastructure.models.FactPayload

object FactMapper {

    fun map(payload: FactPayload): Fact {
        return Fact()
    }

}