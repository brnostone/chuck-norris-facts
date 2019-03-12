package br.com.stone.test.factory

import br.com.stone.domain.Category
import java.util.*

object CategoryFactory {

    private val random = Random()
    private val categories = listOf("explicit", "dev", "movie", "food", "celebrity", "science", "sport", "political", "religion", "animal", "history")

    fun stub() = Category(categories[random.nextInt(categories.size)])

    fun stubList() = categories.map { Category(it) }

}