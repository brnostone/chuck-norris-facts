package br.com.stone.challenge.feature.factory

import java.util.*

object CategoryFactory {

    private val random = Random()
    private val categories = listOf("explicit", "dev", "movie", "food", "celebrity", "science")

    fun create() = categories[random.nextInt(categories.size)]

}