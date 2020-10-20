package com.example.recipesandroidapp.presentation.subFeatures.sorting

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.example.recipesandroidapp.R
import com.example.recipesandroidapp.domain.*
import kotlinx.android.synthetic.main.layout_sorting.*

class SortingDialog : DialogFragment() {

    private val repository by lazy { preferenceRepository }
    private val items = listOf("None", CALORIES, PROTEIN, CARBOS, FAT)
    private val selectedItem by lazy { items.indexOf(repository.getSortKey()) }
    private val adapter by lazy { SortingAdapter(items, selectedItem, this::onClicked) }
    val keyLiveData by lazy { MutableLiveData<String>() }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.layout_sorting, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        width_view.layoutParams.width = (getScreenWidth() * 0.90).toInt()
        sorting_recycler.adapter = adapter
    }

    private fun onClicked(value: String) {
        repository.setSortKey(value)
        keyLiveData.value = value
        dismiss()
    }

    @Suppress("DEPRECATION")
    fun getScreenWidth(): Int = DisplayMetrics()
            .also { requireActivity().windowManager.defaultDisplay.getMetrics(it) }
            .run { widthPixels }
}