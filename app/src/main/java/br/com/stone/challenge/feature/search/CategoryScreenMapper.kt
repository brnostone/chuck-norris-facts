package br.com.stone.challenge.feature.search

import br.com.stone.domain.Category

object CategoryScreenMapper {

    private fun map(category: Category): CategoryScreen {
        return CategoryScreen(
            name = category.name
        )
    }

    fun map(categories: List<Category>) = categories.map { map(it) }

}