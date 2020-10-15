package com.example.recipesandroidapp.domain

import androidx.lifecycle.LiveData
import com.example.recipesandroidapp.domain.dataSources.RecipesApi
import com.example.recipesandroidapp.domain.dataSources.databaseGateway.RecipesDao
import com.example.recipesandroidapp.domain.dataSources.databaseGateway.databaseInstance
import com.example.recipesandroidapp.domain.dataSources.recipesApi
import com.example.recipesandroidapp.entities.InAppRecipe
import com.example.recipesandroidapp.entities.Recipe
import retrofit2.Response

object RecipesRepositorySingleton {

    @Volatile
    private var repository: RecipesRepository? = null

    fun instance() = synchronized(this) { repository ?: DefaultRecipesRepository() }
}

interface RecipesRepository {
    suspend fun refreshRecipes(recipes: List<InAppRecipe>)
    suspend fun getRemoteRecipes(): Response<List<Recipe>>
    fun getRecipes(): LiveData<List<InAppRecipe>>
    fun searchRecipes(search: String): LiveData<List<InAppRecipe>>
}

class DefaultRecipesRepository(
        private val dao: RecipesDao = databaseInstance.dao,
        private val api: RecipesApi = recipesApi
) : RecipesRepository {

    override suspend fun refreshRecipes(recipes: List<InAppRecipe>) {
        dao.deleteRecipes()
        dao.insertRecipes(recipes)
    }

    override suspend fun getRemoteRecipes(): Response<List<Recipe>> = api.retrieveRecipes()

    override fun getRecipes(): LiveData<List<InAppRecipe>> = dao.getRecipes()

    override fun searchRecipes(search: String): LiveData<List<InAppRecipe>> = dao
            .search("%$search%")
}