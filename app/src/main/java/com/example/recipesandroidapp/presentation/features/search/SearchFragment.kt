package com.example.recipesandroidapp.presentation.features.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.recipesandroidapp.R
import com.example.recipesandroidapp.entities.InAppRecipe
import com.example.recipesandroidapp.presentation.subFeatures.RecipesAdapter
import com.example.recipesandroidapp.presentation.subFeatures.SortingDialog
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private val adapter by lazy { RecipesAdapter { navigateToDetails(it) } }
    private val sortDialog by lazy { SortingDialog() }
    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[SearchViewModel::class.java]
    }
    private val watcher by lazy {
        object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.search(s.toString())
            }

            override fun afterTextChanged(s: Editable?) = Unit
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_recycler.adapter = adapter
        back_imageView.setOnClickListener { findNavController().navigateUp() }
        filter_imageView.setOnClickListener { sortDialog.show(childFragmentManager, "Dialog") }
        search_edit_text.addTextChangedListener(watcher)
        setObservers()
    }

    private fun setObservers() = with(viewModel) {
        sortDialog.keyLiveData.observe(viewLifecycleOwner) {
            setRecipes(sortRecipesUseCase((recipesLiveData.value ?: listOf())))
        }
        recipesLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun navigateToDetails(recipe: InAppRecipe) = SearchFragmentDirections
            .actionGlobalDetailsFragment(recipe)
            .let { findNavController().navigate(it) }

    override fun onDestroyView() {
        super.onDestroyView()
        search_edit_text.removeTextChangedListener(watcher)
    }
}
