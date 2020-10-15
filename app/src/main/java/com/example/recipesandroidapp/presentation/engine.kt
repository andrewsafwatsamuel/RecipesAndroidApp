package com.example.recipesandroidapp.presentation

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadPhoto(url:String?)=Glide
    .with(context)
    .load(url?:"")
    .into(this)

fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide(){
    visibility = View.GONE
}

fun View.enable(){
    isEnabled = true
}

fun View.disable(){
    isEnabled = false
}


