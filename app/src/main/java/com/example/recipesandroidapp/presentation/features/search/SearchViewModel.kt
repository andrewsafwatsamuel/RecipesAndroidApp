package com.example.recipesandroidapp.presentation.features.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipesandroidapp.domain.SearchRecipesUseCase
import com.example.recipesandroidapp.domain.SortRecipesUseCase
import com.example.recipesandroidapp.entities.InAppRecipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel(
        val recipesLiveData: MutableLiveData<List<InAppRecipe>> = MutableLiveData(),
        val sortRecipesUseCase: SortRecipesUseCase = SortRecipesUseCase(),
       private val searchRecipesUseCase: SearchRecipesUseCase = SearchRecipesUseCase()
) : ViewModel() {
    private val job by lazy { Job() }

    fun search(key: String,scope:CoroutineScope= CoroutineScope(job+Dispatchers.IO))=scope.launch {
        try {
            searchRecipesUseCase(key,recipesLiveData)
        }catch (e:Exception){e.printStackTrace()}
    }
    fun setRecipes(recipes:List<InAppRecipe>){
        recipesLiveData.value = recipes
    }
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}