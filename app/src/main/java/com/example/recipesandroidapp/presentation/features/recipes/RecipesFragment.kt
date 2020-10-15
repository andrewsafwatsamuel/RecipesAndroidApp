package com.example.recipesandroidapp.presentation.features.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.recipesandroidapp.R
import com.example.recipesandroidapp.domain.ErrorState
import com.example.recipesandroidapp.domain.LoadingState
import com.example.recipesandroidapp.domain.RefreshRecipesState
import com.example.recipesandroidapp.domain.SuccessState
import com.example.recipesandroidapp.presentation.disable
import com.example.recipesandroidapp.presentation.enable
import com.example.recipesandroidapp.presentation.hide
import com.example.recipesandroidapp.presentation.show
import kotlinx.android.synthetic.main.fragment_recipes.*


class RecipesFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this, RecipesViewModelProvider(true))[RecipesViewModel::class.java]
    }

    private val recipesAdapter by lazy { RecipesAdapter { } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_recipes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipes_recycler_view.adapter = recipesAdapter
        retry_button.setOnClickListener { viewModel.refreshRecipes(true) }
        recipes_swipeRefresh.setOnRefreshListener {
            viewModel.refreshRecipes(true)
            recipes_swipeRefresh.isRefreshing = false
        }
        startObservers()
    }

    private fun startObservers() = with(viewModel) {
        statesLiveData.observe(viewLifecycleOwner) { drawStates(it) }
        recipesLiveData.observe(viewLifecycleOwner) {
            empty_view.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.GONE
            recipesAdapter.submitList(sortRecipes(it))
        }
    }

    private fun drawStates(state: RefreshRecipesState) = when (state) {
        is LoadingState -> onLoading()
        is SuccessState -> onSuccess()
        is ErrorState -> onError(state.message)
    }

    private fun onLoading() {
        sort_imageView.disable()
        search_TextView.disable()
        recipes_recycler_view.hide()
        recipes_progressBar.show()
        error_cardView.hide()
        empty_view.hide()
    }

    private fun onSuccess() {
        sort_imageView.enable()
        search_TextView.enable()
        recipes_recycler_view.show()
        recipes_progressBar.hide()
        error_cardView.hide()
    }
    private fun onError(message: String) {
        sort_imageView.enable()
        search_TextView.enable()
        recipes_recycler_view.show()
        recipes_progressBar.hide()
        error_cardView.show()
        error_textView.text = message
    }

}