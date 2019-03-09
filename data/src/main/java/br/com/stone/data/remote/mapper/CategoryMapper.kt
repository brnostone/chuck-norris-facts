package br.com.stone.data.remote.mapper

import br.com.stone.data.remote.model.CategoryPayload
import br.com.stone.domain.Category

object CategoryMapper {

    fun map(payload: CategoryPayload): Category {
        return Category(
            name = payload
        )
    }

    fun map(payloads: List<CategoryPayload>) = payloads.map { map(it) }

}