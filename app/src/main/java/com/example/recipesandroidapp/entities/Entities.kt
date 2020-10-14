package com.example.recipesandroidapp.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

data class Recipe(
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
)


@Parcelize
@Entity
data class InAppRecipe(
        @PrimaryKey
        val id: String,
        val calories: Int?,
        val carbos: Int?,
        val description: String?,
        val difficulty: Int?,
        val fats: Int?,
        val image: String?,
        val name: String?,
        val proteins: Int?,
        val thumb: String?,
        val time: String?
):Parcelable
