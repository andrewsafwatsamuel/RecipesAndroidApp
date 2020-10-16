package com.example.recipesandroidapp.presentation.features.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesandroidapp.R
import com.example.recipesandroidapp.databinding.ItemRecipesGridBinding
import com.example.recipesandroidapp.entities.InAppRecipe
import com.example.recipesandroidapp.presentation.loadPhoto

class RecipesViewHolder(val binding: ItemRecipesGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
    fun bind(item: InAppRecipe) = with(item) {
        binding.itemRecipeImageView.loadPhoto(thumb)
        binding.itemRecipeTextView.text = name
    }
}

class RecipesAdapter(
        private val items: MutableList<InAppRecipe> = mutableListOf(),
        val onClick: (InAppRecipe) -> Unit
) : RecyclerView.Adapter< RecipesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_recipes_grid, parent, false)
            .let { ItemRecipesGridBinding.bind(it) }
            .let { RecipesViewHolder(it) }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) = items[position]
            .let { item ->
                holder.bind(item)
                holder.binding.root.setOnClickListener { onClick(item) }
            }

    fun submitList(newItems:List<InAppRecipe>){
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int =items.size
}