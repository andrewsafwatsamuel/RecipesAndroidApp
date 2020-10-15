package com.example.recipesandroidapp.domain

import androidx.lifecycle.MutableLiveData
import com.example.recipesandroidapp.entities.InAppRecipe
import com.example.recipesandroidapp.entities.Recipe
import org.jetbrains.annotations.TestOnly

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