package br.com.stone.challenge.feature.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.stone.challenge.R
import kotlinx.android.synthetic.main.activity_facts.*

class SearchActivity : AppCompatActivity() {

    companion object {

        fun launchIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}