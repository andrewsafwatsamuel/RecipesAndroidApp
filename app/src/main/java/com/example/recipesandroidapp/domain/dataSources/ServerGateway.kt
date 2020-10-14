package com.example.recipesandroidapp.domain.dataSources

import com.example.recipesandroidapp.entities.Recipe
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://hf-android-app.s3-eu-west-1.amazonaws.com/android-test/"

private val retrofitInstance by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

val recipesApi: RecipesApi by lazy {
    retrofitInstance.create(RecipesApi::class.java)
}

interface RecipesApi {
    @GET("recipes.json")
    suspend fun retrieveRecipes(): Response<List<Recipe>>
}