package br.com.stone.challenge.feature.facts

import br.com.stone.domain.Fact

object FactScreenMapper {

    fun map(fact: Fact): FactScreen {
        val textType = if (fact.text.length  <= 80)
            TextType.BIG
        else
            TextType.NORMAL

        val categories = ArrayList<CategoryScreen>()
        if (fact.categories.isEmpty())
            categories += CategoryScreen.Uncategorized
        else {
            fact.categories.mapTo(categories) { category ->
                CategoryScreen.Categorized(category)
            }
        }

        return FactScreen(
            text = fact.text,
            url = fact.url,
            textType = textType,
            categories = categories
        )
    }

    fun map(facts: List<Fact>) = facts.map { map(it) }

}