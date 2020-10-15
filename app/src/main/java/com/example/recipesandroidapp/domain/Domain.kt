package com.example.recipesandroidapp.domain

import android.content.Context
import androidx.lifecycle.MutableLiveData

fun <T> T.toMutableLiveData() = MutableLiveData<T>()
    .also { it.value = this }

object Domain {

    lateinit var appContext: Context
        private set

    fun setContext(context: Context) {
        appContext = context
    }

}
