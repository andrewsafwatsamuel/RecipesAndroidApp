package com.example.recipesandroidapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.recipesandroidapp.R
import com.example.recipesandroidapp.domain.dataSources.databaseGateway.databaseInstance
import com.example.recipesandroidapp.entities.InAppRecipe
import com.example.recipesandroidapp.entities.Recipe
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}