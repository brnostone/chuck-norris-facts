package br.com.stone.challenge.feature.facts

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.stone.challenge.R
import br.com.stone.challenge.feature.common.ViewState
import br.com.stone.challenge.feature.search.SearchActivity
import kotlinx.android.synthetic.main.activity_facts.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FactsActivity : AppCompatActivity() {

    private val viewModel: FactsViewModel by viewModel()
    private val facts = ArrayList<FactScreen>()

    companion object {
        private const val RESULT_SEARCH = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facts)

        setSupportActionBar(toolbar)

        setupRecycler()
        bindObserver()
    }

    private fun setupRecycler() = with(recyclerFacts) {
        layoutManager = LinearLayoutManager(context)
        adapter = FactsAdapter(facts, onClickShare = { fact ->
            shareUrl(fact.url)
        })
    }

    private fun bindObserver() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is ViewState.Default -> showInitialLayout()
                is ViewState.Loading -> showLoading()
                is ViewState.Success -> {
                    hideLoading()
                    updateList(state.data)
                }
                is ViewState.Failed -> showError(state.throwable)
            }
        })

        btnSearchFact.setOnClickListener {
            openSearchScreen()
        }

        lifecycle.addObserver(viewModel)
    }

    private fun showInitialLayout() {
        emptyLayout.isVisible = true
        txtEmptyTitle.setText(R.string.message_welcome)
    }

    private fun showLoading() {
        progressBar.isVisible = true
        errorView.isVisible = false
        emptyLayout.isVisible = false
    }

    private fun hideLoading() {
        progressBar.isVisible = false
    }

    private fun updateList(newFacts: List<FactScreen>) {
        emptyLayout.isVisible = newFacts.isEmpty()
        txtEmptyTitle.setText(R.string.message_no_results)

        recyclerFacts.scrollToPosition(0)

        facts.clear()
        facts += newFacts

        recyclerFacts.adapter?.notifyDataSetChanged()
    }

    private fun showError(throwable: Throwable) {
        Timber.e(throwable)
        errorView.isVisible = true
        errorView.setError(throwable)
    }

    private fun shareUrl(url: String) {
        ShareCompat.IntentBuilder.from(this)
            .setType("text/plain")
            .setChooserTitle(R.string.title_share_url)
            .setText(url)
            .startChooser()
    }

    private fun openSearchScreen() {
        val intent = SearchActivity.launchIntent(this)
        startActivityForResult(intent, RESULT_SEARCH)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_facts, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_search) {
            openSearchScreen()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_SEARCH) {
                data?.getStringExtra(SearchActivity.EXTRA_SEARCH_TERM)?.let { term ->
                    viewModel.search(term)
                }
            }
        }
    }

}
