package br.com.stone.challenge.feature.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.stone.challenge.R
import br.com.stone.challenge.util.extensions.inflate
import kotlinx.android.synthetic.main.item_adapter_historic.view.*

class LastSearchAdapter(
        private val terms: List<String>,
        private val onClickItem: (String) -> Unit) : RecyclerView.Adapter<LastSearchAdapter.ViewHolder>() {

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_adapter_historic)) {

        fun bind(term: String) = with(itemView) {
            txtTitle.text = term

            setOnClickListener { onClickItem(term) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)
    override fun getItemCount() = terms.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(terms[position])

}