package br.com.stone.challenge.feature.screen

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import br.com.stone.challenge.R
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.edit.KTextInputLayout
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import org.hamcrest.Matcher

class SearchScreen: Screen<SearchScreen>() {
    val inputLayoutSearch = KTextInputLayout { withId(R.id.inputLayoutSearch) }
    val recyclerHistoric = KRecyclerView(builder = { withId(R.id.recyclerHistoric) }, itemTypeBuilder = { itemType(SearchScreen::Item) })
    val suggestionTagView = KView(withId(R.id.layoutSuggestions)) { withIndex(0) { } }

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val txtTitle = KTextView(parent) { withId(R.id.txtTitle) }
    }
}