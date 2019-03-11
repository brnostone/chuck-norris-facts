package br.com.stone.challenge.feature.factory

import br.com.stone.domain.Category
import java.util.*

object CategoryFactory {

    private val random = Random()
    private val categories = listOf("explicit", "dev", "movie", "food", "celebrity", "science")

    fun stub() = Category(categories[random.nextInt(categories.size)])

    fun stubList() = categories.map { Category(it) }

}