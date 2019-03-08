package br.com.stone.challenge.feature.factory

import br.com.stone.domain.Fact
import java.util.*

object FactFactory {

    private val random = Random()

    fun create(hasCategory: Boolean = true, bigText: Boolean = true): Fact {
        val categories = arrayListOf<String>()
        val randomId = UUID.randomUUID().toString()
        var repeatText = random.nextInt(6) + 1

        if (hasCategory)
            categories += CategoryFactory.create()

        if (bigText)
            repeatText += 10

        return Fact(
            categories = categories,
            url = "https://api.chucknorris.io/jokes/$randomId",
            text = "Chuck Norris".repeat(repeatText)
        )
    }

}