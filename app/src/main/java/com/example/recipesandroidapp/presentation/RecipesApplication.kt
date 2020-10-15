package com.example.recipesandroidapp.presentation

import android.app.Application
import com.example.recipesandroidapp.domain.Domain
import timber.log.Timber

class RecipesApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Domain.setContext(applicationContext)
        Timber.plant(Timber.DebugTree())
    }
}