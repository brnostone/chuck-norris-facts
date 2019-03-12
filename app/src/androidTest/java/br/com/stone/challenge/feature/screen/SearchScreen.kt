package br.com.stone.challenge.feature.screen

import android.view.View
import br.com.stone.challenge.R
import com.agoda.kakao.edit.KTextInputLayout
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import org.hamcrest.Matcher

class SearchScreen: Screen<SearchScreen>() {
    val inputLayoutSearch = KTextInputLayout { withId(R.id.inputLayoutSearch) }
    val recyclerHistoric = KRecyclerView(builder = { withId(R.id.recyclerHistoric) }, itemTypeBuilder = { itemType(FactsScreen::Item) })

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val txtTitle = KTextView(parent) { withId(R.id.txtTitle) }
    }
}