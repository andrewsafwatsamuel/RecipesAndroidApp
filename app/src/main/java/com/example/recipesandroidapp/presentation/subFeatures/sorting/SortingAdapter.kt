package com.example.recipesandroidapp.presentation.subFeatures.sorting

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesandroidapp.R
import com.example.recipesandroidapp.presentation.inflateView


@Suppress("HasPlatformType")
class SortingViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val sortingTextView by lazy { view.findViewById<TextView>(R.id.sorting_textView) }

    fun bind(item: String) = with(sortingTextView) {
        text = item
    }


}

class SortingAdapter(
        private val items: List<String>,
        selectedItem: Int,
        private val onItemSelected: (String) -> Unit
) : RecyclerView.Adapter<SortingViewHolder>() {
    private var selectedPosition = -1

    init {
        this.selectedPosition = selectedItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = parent
            .inflateView(R.layout.item_sorting)
            .let { SortingViewHolder(it) }

    override fun onBindViewHolder(holder: SortingViewHolder, position: Int) = with(holder) {
        bind(items[position])
        sortingTextView.background = ContextCompat
                .getDrawable(holder.view.context, setSortingBackground(selectedPosition == position))
        view.setOnClickListener {
            onItemSelected(items[position])
            selectedPosition = position
            notifyDataSetChanged()
        }
    }

    private fun setSortingBackground(isSelected: Boolean) =
            if (isSelected) R.drawable.selected_sorting_text_background
            else R.color.white

    override fun getItemCount() = items.size
}