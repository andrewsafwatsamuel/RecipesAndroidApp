package com.example.recipesandroidapp.presentation.features.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.recipesandroidapp.R
import com.example.recipesandroidapp.domain.CALORIES
import com.example.recipesandroidapp.domain.CARPOS
import com.example.recipesandroidapp.domain.FAT
import com.example.recipesandroidapp.domain.PROTEIN
import com.example.recipesandroidapp.entities.NutritionalFactHelper
import com.example.recipesandroidapp.presentation.loadPhoto
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private val recipe by lazy { DetailsFragmentArgs.fromBundle(requireArguments()).recipe }
    private val adapter by lazy {
        NutritionalFactsAdapter(
            listOf(
                NutritionalFactHelper(CALORIES, recipe.calories, "kcal"),
                NutritionalFactHelper(PROTEIN, recipe.proteins, "g"),
                NutritionalFactHelper(CARPOS, recipe.carbos, "g"),
                NutritionalFactHelper(FAT, recipe.fats, "g")
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_details, container, false)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawToolbar()
        nutritional_facts_recycler.adapter = adapter
        headline_textView.text = recipe.headline ?: "???"
        recipe_imageView.loadPhoto(recipe.image)
        difficulty_textView.text = "Difficulty : ${recipe.difficulty ?: "???"}"
        duration_textView.text = "Ready in ${recipe.time?.substring(2) ?: "???"}"
        description.text = recipe.description ?: "???"
    }

    private fun drawToolbar() = with(details_toolbar) {
        setupWithNavController(findNavController())
        title = recipe.name
    }
}
