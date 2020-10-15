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

private val recipesUtil by lazy {
    object : DiffUtil.ItemCallback<InAppRecipe>() {
        override fun areItemsTheSame(oldItem: InAppRecipe, newItem: InAppRecipe) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: InAppRecipe, newItem: InAppRecipe) =
            oldItem == newItem
    }

}

class RecipesViewHolder(val binding: ItemRecipesGridBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: InAppRecipe) = with(item) {
        binding.itemRecipeImageView.loadPhoto(thumb)
        binding.itemRecipeTextView.text = name
    }
}

class RecipesAdapter(
    val onClick: (InAppRecipe) -> Unit
) : ListAdapter<InAppRecipe, RecipesViewHolder>(recipesUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LayoutInflater
        .from(parent.context)
        .inflate(R.layout.item_recipes_grid, parent, false)
        .let { ItemRecipesGridBinding.bind(it) }
        .let { RecipesViewHolder(it) }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) = getItem(position)
        .let { item ->
            holder.bind(item)
            holder.binding.root.setOnClickListener { onClick(item) }
        }
}