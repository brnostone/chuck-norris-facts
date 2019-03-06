package br.com.stone.challenge.facts

import android.util.TypedValue
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.stone.challenge.R
import br.com.stone.challenge.common.extensions.inflate
import kotlinx.android.synthetic.main.item_adapter_fact.view.*

class FactsAdapter(private val facts: List<FactScreen>) : RecyclerView.Adapter<FactsAdapter.ViewHolder>() {

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_adapter_fact)) {

        fun bind(fact: FactScreen) = with(itemView) {
            txtDescription.text = fact.text
            txtDescription.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(fact.textType.dimen))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)
    override fun getItemCount() = facts.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(facts[position])

}