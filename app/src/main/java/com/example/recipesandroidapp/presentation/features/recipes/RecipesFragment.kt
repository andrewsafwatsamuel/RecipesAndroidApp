package com.example.recipesandroidapp.presentation.features.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.recipesandroidapp.R
import com.example.recipesandroidapp.domain.*
import com.example.recipesandroidapp.entities.InAppRecipe
import com.example.recipesandroidapp.presentation.*
import com.example.recipesandroidapp.presentation.subFeatures.RecipesAdapter
import com.example.recipesandroidapp.presentation.subFeatures.SortingDialog
import kotlinx.android.synthetic.main.fragment_recipes.*


class RecipesFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this, RecipesViewModelProvider(requireContext().checkConnectivity()))[RecipesViewModel::class.java]
    }

    private val recipesAdapter by lazy { RecipesAdapter { navigateToDetails(it) } }

    private val sortingDialog by lazy { SortingDialog() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_recipes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sorting_cardView.setOnClickListener { sortingDialog.show(childFragmentManager,"") }
        recipes_recycler_view.adapter = recipesAdapter
        retry_button.setOnClickListener { viewModel.refreshRecipes(requireContext().checkConnectivity()) }
        recipes_swipeRefresh.setOnRefreshListener {
            viewModel.refreshRecipes(requireContext().checkConnectivity())
            recipes_swipeRefresh.isRefreshing = false
        }
        startObservers()
        search_TextView.setOnClickListener { findNavController().navigate(R.id.action_recipesFragment_to_searchFragment) }
    }

    private fun startObservers() = with(viewModel) {
        statesLiveData.observe(viewLifecycleOwner) { drawStates(it) }
        recipesLiveData.observe(viewLifecycleOwner) {
            empty_view.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.GONE
            recipesAdapter.submitList(sortRecipes(it))
        }
        sortingDialog.keyLiveData.observe(viewLifecycleOwner) {
            setRecipes(sortRecipes((recipesLiveData.value ?: listOf())))
        }
    }

    private fun drawStates(state: RefreshRecipesState) = when (state) {
        is LoadingState -> onLoading()
        is SuccessState -> onSuccess()
        is ErrorState -> onError(state.message)
    }

    private fun onLoading() {
        sorting_cardView.disable()
        search_cardView.disable()
        recipes_recycler_view.hide()
        recipes_progressBar.show()
        error_cardView.hide()
        empty_view.hide()
    }

    private fun onSuccess() {
        sorting_cardView.enable()
        search_cardView.enable()
        recipes_recycler_view.show()
        recipes_progressBar.hide()
        error_cardView.hide()
    }
    private fun onError(message: String) {
        sorting_cardView.enable()
        search_cardView.enable()
        recipes_recycler_view.show()
        recipes_progressBar.hide()
        error_cardView.show()
        error_textView.text = message
    }

    private fun navigateToDetails(recipe: InAppRecipe)= RecipesFragmentDirections
            .actionGlobalDetailsFragment(recipe)
            .let { findNavController().navigate(it) }
}