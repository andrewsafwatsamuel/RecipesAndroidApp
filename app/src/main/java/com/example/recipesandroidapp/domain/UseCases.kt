package com.example.recipesandroidapp.domain

import androidx.lifecycle.MutableLiveData
import com.example.recipesandroidapp.entities.InAppRecipe
import com.example.recipesandroidapp.entities.Recipe

sealed class RefreshRecipesState
object LoadingState : RefreshRecipesState()
object SuccessState : RefreshRecipesState()
class ErrorState(val message: String) : RefreshRecipesState()

class RefreshRecipesUseCase(
    private val repository: RecipesRepository = RecipesRepositorySingleton.instance()
) {

    suspend fun invoke(
        isConnected: Boolean,
        states: MutableLiveData<RefreshRecipesState>
    ) = repository
        .takeIf { isConnected }
        ?.takeUnless { states.value is LoadingState }
        ?.also { states.postValue(LoadingState) }
        ?.run { getRemoteRecipes() }
        ?.run {
            if (isSuccessful) refresh(body() ?: listOf())
            states.postValue(if (isSuccessful) SuccessState else ErrorState(message()))
        }

    private suspend fun refresh(recipes: List<Recipe>) = recipes
        .map { it.toInAppRecipe() }
        .let { repository.refreshRecipes(it) }

    private fun Recipe.toInAppRecipe() = InAppRecipe(
        id,
        headline,
        calories?.toIntValue(),
        carbos?.toIntValue(),
        description,
        difficulty,
        fats?.toIntValue(),
        image,
        name,
        proteins?.toIntValue(),
        thumb,
        time
    )

    private fun String.toIntValue() = takeUnless { it.isEmpty() }
        ?.run { split(" ")[0].toInt() } ?: 0
}

class SearchRecipesUseCase(
    private val repository: RecipesRepository = RecipesRepositorySingleton.instance()
) {
    operator fun invoke(searchKey: String, recipes: MutableLiveData<List<InAppRecipe>>) = repository
        .takeIf { searchKey.isNotEmpty() }
        ?.searchRecipes(searchKey)
        ?.let { recipes.postValue(it) }
}


const val CALORIES = "calories"
const val FAT = "fat"
const val PROTEIN = "protein"
const val CARPOS = "carpos"

class SortRecipesUseCase(val repository: PreferenceRepository= preferenceRepository) {
    operator fun invoke(recipes: List<InAppRecipe>) = recipes
        .asSequence()
        .sortedBy { it.sort() }
        .toList()

    private fun InAppRecipe.sort() = when (repository.getSortKey()) {
        FAT -> fats
        PROTEIN -> proteins
        CARPOS -> carbos
        CALORIES -> calories
        else -> -1
    }

}