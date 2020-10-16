package com.example.recipesandroidapp.presentation.features.details

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesandroidapp.R
import com.example.recipesandroidapp.databinding.ItemNutritionalFactBinding
import com.example.recipesandroidapp.entities.NutritionalFactHelper
import com.example.recipesandroidapp.presentation.inflateView

class NutritionalFactsHolder(private val binding: ItemNutritionalFactBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: NutritionalFactHelper) = with(item) {
        binding.titleTextView.text = title ?: "???"
        binding.unitTextView.text = unit ?: "???"
        binding.valueTextView.text = (value ?: 0).toString()
    }
}

class NutritionalFactsAdapter(private val items: List<NutritionalFactHelper>) :
    RecyclerView.Adapter<NutritionalFactsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = parent
        .inflateView(R.layout.item_nutritional_fact)
        .let { ItemNutritionalFactBinding.bind(it) }
        .let { NutritionalFactsHolder(it) }

    override fun onBindViewHolder(holder: NutritionalFactsHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size
}