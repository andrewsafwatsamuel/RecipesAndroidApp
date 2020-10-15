package com.example.recipesandroidapp.presentation.features.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipesandroidapp.domain.*
import com.example.recipesandroidapp.entities.InAppRecipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalStateException

class RecipesViewModel(
    isConnected: Boolean,
    val recipesLiveData: LiveData<List<InAppRecipe>> = RecipesRepositorySingleton.instance().getRecipes(),
    val statesLiveData: MutableLiveData<RefreshRecipesState> = MutableLiveData(),
    private val refreshRecipesUseCase: RefreshRecipesUseCase = RefreshRecipesUseCase(),
    private val sortRecipesUseCase: SortRecipesUseCase = SortRecipesUseCase(),
) : ViewModel() {

    private val recipesJob = Job()

    init {
        refreshRecipes(isConnected)
    }

    fun sortRecipes(recipes: List<InAppRecipe>) = sortRecipesUseCase(recipes)

    fun refreshRecipes(
        isConnected: Boolean,
        scope: CoroutineScope = CoroutineScope(recipesJob + Dispatchers.IO)
    ) = scope.launch {
        try {
            refreshRecipesUseCase.invoke(isConnected, statesLiveData)
        } catch (e: Exception) {
            statesLiveData.postValue(ErrorState(e.message ?: "Un Known Error"))
        }
    }
}

class RecipesViewModelProvider(private val isConnected: Boolean) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(RecipesViewModel::class.java)) RecipesViewModel(isConnected) as T
        else throw IllegalStateException("Bad ViewModel Class")


}