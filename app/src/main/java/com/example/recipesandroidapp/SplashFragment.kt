package com.example.recipesandroidapp

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import java.util.*

class SplashFragment : Fragment() {

    private val timer = Timer()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_splash, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timer .schedule(object :TimerTask(){
            override fun run() {
                findNavController().navigate(R.id.action_splashFragment_to_recipesFragment)
            }
        },2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }

}