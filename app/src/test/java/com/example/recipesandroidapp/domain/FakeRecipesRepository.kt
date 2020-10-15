package com.example.recipesandroidapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipesandroidapp.entities.InAppRecipe
import com.example.recipesandroidapp.entities.Recipe
import retrofit2.Response

class FakeRecipesRepository(
    private val response: Response<List<Recipe>> = Response.success(
        listOf(
            Recipe("burger", "102 C", "10 g", "", 0, "10 g", "", "burger", "10 g", "", ""),
            Recipe("burger", "102 C", "10 g", "", 0, "10 g", "", "salamon", "10 g", "", ""),
            Recipe("burger", "", "10 g", "", 0, "10 g", "", "steak", "10 g", "", ""),
            Recipe("burger", "102 C", "10 g", "", 0, "10 g", "", "burger", "10 g", "", ""),
            Recipe("burger", "102 C", "10 g", "", 0, "10 g", "", "salamon", "10 g", "", ""),
            Recipe("burger", "102 C", "10 g", "", 0, "10 g", "", "steak", "10 g", "", "")
        )
    )
) : RecipesRepository {


    private val localLiveData = MutableLiveData<List<InAppRecipe>>()

    override suspend fun refreshRecipes(recipes: List<InAppRecipe>) {
        localLiveData.postValue(recipes)
    }

    override suspend fun getRemoteRecipes(): Response<List<Recipe>> = response

    override fun getRecipes(): LiveData<List<InAppRecipe>> = localLiveData

    override fun searchRecipes(search: String): List<InAppRecipe> = listOf()
}