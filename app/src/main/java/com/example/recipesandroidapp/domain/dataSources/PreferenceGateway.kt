package com.example.recipesandroidapp.domain.dataSources

import android.content.Context
import com.example.recipesandroidapp.domain.Domain

const val SORT_PREFERENCE = "sortPreference"
const val RECIPE_PREFERENCES = "preferences"

object Preferences {

    private fun preferences(key: String = RECIPE_PREFERENCES) = Domain.appContext
        .getSharedPreferences(key, Context.MODE_PRIVATE)

    fun putString(key: String, value: String) = preferences()
        .edit()
        .remove(key)
        .putString(key, value)
        .apply()

    fun getString(key: String)= preferences().getString(key,"") ?:""
}