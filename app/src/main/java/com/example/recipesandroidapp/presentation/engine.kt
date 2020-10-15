package com.example.recipesandroidapp.presentation

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadPhoto(url:String?)=Glide
    .with(context)
    .load(url?:"")
    .into(this)