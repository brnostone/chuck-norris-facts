package br.com.stone.challenge.feature.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.plusAssign
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.stone.challenge.R
import br.com.stone.challenge.feature.common.ViewState
import br.com.stone.challenge.util.extensions.inflate
import br.com.stone.challenge.util.extensions.onActionSearch
import br.com.stone.challenge.util.extensions.textWatcher
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.view_category.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModel()
    private val historic = ArrayList<String>()

    companion object {
        const val EXTRA_SEARCH_TERM = "search_term"

        fun launchIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecycler()
        bindObserver()
    }

    private fun setupRecycler() = with(recyclerHistoric) {
        layoutManager = LinearLayoutManager(context)
        adapter = LastSearchAdapter(historic) { text ->
            search(text)
        }
    }

    private fun bindObserver() {
        viewModel.getCategoriesState().observe(this, Observer { state ->
            when (state) {
                is ViewState.Loading -> showLoadingSuggestions()
                is ViewState.Success -> {
                    hideLoadingSuggestions()
                    makeSuggestionList(state.data)
                }
                is ViewState.Failed -> showError(state.throwable)
            }
        })

        viewModel.getHistoricState().observe(this, Observer { state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> updateHistoricList(state.data)
                is ViewState.Failed -> {}
            }
        })

        viewModel.getIsSearchValid().observe(this, Observer { isValid ->
            if (isValid)
                inputLayoutSearch.isErrorEnabled = false
            else
                inputLayoutSearch.error = getString(R.string.error_invalid_term_size)
        })

        inputSearch.onActionSearch {
            if (viewModel.getIsSearchValid().value == true)
                search(viewModel.search)
        }

        inputSearch.textWatcher {
            onTextChanged { term, _, _, _ ->
                viewModel.search = term ?: ""
            }
        }

        lifecycle.addObserver(viewModel)
    }

    private fun showLoadingSuggestions() {
        errorViewSuggestions.isVisible = false
        progressSuggestions.isVisible = true
    }

    private fun hideLoadingSuggestions() {
        progressSuggestions.isVisible = false
    }

    private fun showError(throwable: Throwable) {
        Timber.e(throwable)
        errorViewSuggestions.isVisible = true
        errorViewSuggestions.setError(throwable)
    }

    private fun makeSuggestionList(categories: List<CategoryScreen>) {
        layoutSuggestions.removeAllViews()

        categories.forEach { category ->
            val view = layoutSuggestions.inflate(R.layout.view_category)

            view.txtTitle.text = category.name
            view.setOnClickListener { search(category.name) }

            layoutSuggestions += view
        }
    }

    private fun updateHistoricList(data: List<String>) {
        txtHistoric.isVisible = data.isNotEmpty()

        historic.clear()
        historic += data

        recyclerHistoric.adapter?.notifyDataSetChanged()
    }

    private fun search(term: String) {
        intent.putExtra(EXTRA_SEARCH_TERM, term)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}