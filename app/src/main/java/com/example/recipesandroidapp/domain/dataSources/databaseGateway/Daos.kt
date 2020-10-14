package com.example.recipesandroidapp.domain.dataSources.databaseGateway

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipesandroidapp.entities.InAppRecipe

@Dao
interface RecipesDao {
    @Query("SELECT * FROM InAppRecipe")
    fun getRecipes(): LiveData<List<InAppRecipe>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecipes(recipes: List<InAppRecipe>)

    @Query("DELETE FROM InAppRecipe")
    fun deleteRecipes()

    @Query("SELECT * FROM InAppRecipe WHERE id LIKE :id")
    fun getRecipe(id: String): InAppRecipe

}