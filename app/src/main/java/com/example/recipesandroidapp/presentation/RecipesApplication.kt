package com.example.recipesandroidapp.presentation

import android.app.Application
import com.example.recipesandroidapp.domain.Domain

class RecipesApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Domain.setContext(applicationContext)
    }
}