package br.com.stone.challenge.feature.facts

import br.com.stone.domain.Fact

object FactScreenMapper {

    fun map(fact: Fact): FactScreen {
        val textType = if (fact.text.length  <= 80)
            TextType.BIG
        else
            TextType.NORMAL

        return FactScreen(
            text = fact.text,
            url = fact.url,
            textType = textType
        )
    }

    fun map(facts: List<Fact>) = facts.map { map(it) }

}