package com.example.recipesandroidapp.presentation.features.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recipesandroidapp.R


class RecipesFragment : Fragment() {

    val viewModel by lazy {
        ViewModelProvider(this, RecipesViewModelProvider(true))[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_recipes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObservers()
    }

    private fun startObservers()= with(viewModel){
        statesLiveData.observe(viewLifecycleOwner) {  }
        recipesLiveData.observe(viewLifecycleOwner) {  }
    }

}