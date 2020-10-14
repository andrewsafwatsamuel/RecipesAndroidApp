package com.example.recipesandroidapp.domain

import android.content.Context


object Domain{

    lateinit var appContext: Context
    private set

    fun setContext(context: Context){
        appContext = context
    }

}
