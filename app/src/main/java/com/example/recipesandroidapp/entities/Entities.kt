package com.example.recipesandroidapp.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Recipe(
        @PrimaryKey
        val id: String,
        val calories: String?,
        val carbos: String?,
        val description: String?,
        val difficulty: Int?,
        val fats: String?,
        val image: String?,
        val name: String?,
        val proteins: String?,
        val thumb: String?,
        val time: String?
) : Parcelable