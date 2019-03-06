package br.com.stone.challenge.facts

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.stone.challenge.R
import br.com.stone.challenge.search.SearchActivity
import kotlinx.android.synthetic.main.activity_facts.*
import org.koin.androidx.viewmodel.ext.android.viewModel

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

        viewModel.facts.observe(this,  Observer { newFacts ->
            facts.clear()
            facts += newFacts

            recyclerFacts.adapter?.notifyDataSetChanged()
        })

        viewModel.search("dev")
    }

    private fun setupRecycler() = with(recyclerFacts) {
        layoutManager = LinearLayoutManager(context)
        adapter = FactsAdapter(facts)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_facts, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_search) {
            val intent = SearchActivity.launchIntent(this)
            startActivityForResult(intent, RESULT_SEARCH)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}
