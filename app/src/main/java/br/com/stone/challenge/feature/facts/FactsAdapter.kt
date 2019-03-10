package br.com.stone.challenge.feature.facts

import android.util.TypedValue
import android.view.ViewGroup
import androidx.core.view.plusAssign
import androidx.recyclerview.widget.RecyclerView
import br.com.stone.challenge.R
import br.com.stone.challenge.util.extensions.inflate
import kotlinx.android.synthetic.main.item_adapter_fact.view.*
import kotlinx.android.synthetic.main.view_category.view.*

class FactsAdapter(
        private val facts: List<FactScreen>,
        private val onClickShare: (FactScreen) -> Unit) : RecyclerView.Adapter<FactsAdapter.ViewHolder>() {

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_adapter_fact)) {

        fun bind(fact: FactScreen) = with(itemView) {
            txtDescription.text = fact.text
            txtDescription.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(fact.textType.dimen))

            categoryContainer.removeAllViews()

            fact.categories.forEach { category ->
                val view = categoryContainer.inflate(R.layout.view_category)
                view.txtTitle.text = when (category) {
                    is CategoryFactScreen.UnCategorized -> context.getString(R.string.uncategorized)
                    is CategoryFactScreen.Categorized -> category.name
                }

                categoryContainer += view
            }

            btnShare.setOnClickListener {
                onClickShare(fact)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)
    override fun getItemCount() = facts.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(facts[position])

}