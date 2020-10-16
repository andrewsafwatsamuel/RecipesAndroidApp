package com.example.recipesandroidapp.domain.dataSources.databaseGateway

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipesandroidapp.domain.Domain
import com.example.recipesandroidapp.entities.InAppRecipe

private const val DATABASE_NAME = "recipes_database"

val databaseInstance by lazy {
    Room.databaseBuilder(Domain.appContext, RecipesDatabase::class.java, DATABASE_NAME).build()
}

@Database(entities = [InAppRecipe::class], version = 1, exportSchema = false)
abstract class RecipesDatabase : RoomDatabase() {
    abstract val dao: RecipesDao
}