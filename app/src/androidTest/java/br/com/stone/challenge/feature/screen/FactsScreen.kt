package br.com.stone.challenge.feature.screen

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import br.com.stone.challenge.R
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import org.hamcrest.Matcher

class FactsScreen: Screen<FactsScreen>() {
    val recyclerFacts = KRecyclerView(builder = { withId(R.id.recyclerFacts) }, itemTypeBuilder = { itemType(FactsScreen::Item) })
    val progressBar = KView { withId(R.id.progressBar) }
    val errorView = KView { withId(R.id.errorView) }
    val emptyLayout = KView { withId(R.id.emptyLayout) }
    val txtEmptyTitle = KTextView { withId(R.id.txtEmptyTitle) }
    val btnSearchFact = KButton { withId(R.id.btnSearchFact) }

    val imgViewError = KImageView(withId(R.id.errorView)) { withId(R.id.imgViewError) }
    val txtViewError = KTextView(withId(R.id.errorView)) { withId(R.id.txtViewError) }


    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val txtDescription = KTextView(parent) { withId(R.id.txtDescription) }
        val categoryContainer = KView(parent) { withId(R.id.categoryContainer) }
        val btnShare = KButton(parent) { withId(R.id.btnShare) }
    }
}