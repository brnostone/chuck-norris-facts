package br.com.stone.challenge.facts

import br.com.stone.challenge.R


enum class TextType(val dimen: Int) {
    BIG(R.dimen.fact_text_big), NORMAL(R.dimen.fact_text_normal)
}

class FactScreen(
    val url: String,
    val text: String,
    val textType: TextType
)