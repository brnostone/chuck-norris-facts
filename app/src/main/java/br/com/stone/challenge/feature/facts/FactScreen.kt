package br.com.stone.challenge.feature.facts

import br.com.stone.challenge.R


enum class TextType(val dimen: Int) {
    BIG(R.dimen.fact_text_big), NORMAL(R.dimen.fact_text_normal)
}

sealed class CategoryScreen {
    object UnCategorized : CategoryScreen()
    class Categorized(val text: String) : CategoryScreen()
}

class FactScreen(
    val url: String,
    val text: String,
    val textType: TextType,
    val categories: List<CategoryScreen>
)