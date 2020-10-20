package com.example.recipesandroidapp.domain

import com.example.recipesandroidapp.domain.dataSources.Preferences
import com.example.recipesandroidapp.domain.dataSources.RecipesApi
import com.example.recipesandroidapp.domain.dataSources.SORT_PREFERENCE
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
    fun getRecipes(): List<InAppRecipe>
    fun searchRecipes(search: String): List<InAppRecipe>
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

    override fun getRecipes(): List<InAppRecipe> = dao.getRecipes()

    override fun searchRecipes(search: String): List<InAppRecipe> = dao.search("%$search%")
}

val preferenceRepository by lazy { DefaultPreferenceRepository() }

interface PreferenceRepository{
    fun setSortKey(sort:String)
    fun getSortKey():String
}

class DefaultPreferenceRepository:PreferenceRepository{
    override fun setSortKey(sort: String) =Preferences.putString(SORT_PREFERENCE,sort)

    override fun getSortKey(): String =  Preferences.getString(SORT_PREFERENCE)?:"None"
}