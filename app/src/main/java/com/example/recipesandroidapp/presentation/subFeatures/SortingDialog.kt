package com.example.recipesandroidapp.presentation.subFeatures

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.recipesandroidapp.R
import com.example.recipesandroidapp.domain.*
import kotlinx.android.synthetic.main.layout_sorting.*

class SortingDialog : DialogFragment() {

    private val repository by lazy { preferenceRepository }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.layout_sorting, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        width_view.layoutParams.width = (getScreenWidth() * 0.90).toInt()
        non_textView.onClicked("None") { selectNon();dismiss() }
        calories_textView.onClicked(CALORIES) { selectCalories() }
        fats_textView.onClicked(FAT) { selectFats() }
        proteins_textView.onClicked(PROTEIN) { selectProteins() }
        carbos_textView.onClicked(CARPOS) { selectCarbos() }

        when(repository.getSortKey()){
            CARPOS->selectCarbos()
            PROTEIN->selectProteins()
            FAT->selectFats()
            CALORIES->selectCalories()
            else->selectNon()
        }
    }

    private fun View.onClicked(value:String,clicked: () -> Unit) = setOnClickListener {
        clicked()
        dismiss()
        repository.setSortKey(value)
    }


    private fun selectNon() {
        non_textView.select()
        calories_textView.deselect()
        fats_textView.deselect()
        proteins_textView.deselect()
        carbos_textView.deselect()
    }

    private fun selectCalories() {
        non_textView.deselect()
        calories_textView.select()
        fats_textView.deselect()
        proteins_textView.deselect()
        carbos_textView.deselect()
    }

    private fun selectFats() {
        non_textView.deselect()
        calories_textView.deselect()
        fats_textView.select()
        proteins_textView.deselect()
        carbos_textView.deselect()
    }

    private fun selectProteins() {
        non_textView.deselect()
        calories_textView.deselect()
        fats_textView.deselect()
        proteins_textView.select()
        carbos_textView.deselect()
    }

    private fun selectCarbos() {
        non_textView.deselect()
        calories_textView.deselect()
        fats_textView.deselect()
        proteins_textView.deselect()
        carbos_textView.select()
    }


    private fun View.select() {
        background = ContextCompat.getDrawable(requireContext(), R.drawable.selected_sorting_text_background)
    }

    fun View.deselect() {
        background = ContextCompat.getDrawable(requireContext(), R.color.white)
    }


    @Suppress("DEPRECATION")
    fun getScreenWidth(): Int = DisplayMetrics()
            .also { requireActivity().windowManager.defaultDisplay.getMetrics(it) }
            .run { widthPixels }
}