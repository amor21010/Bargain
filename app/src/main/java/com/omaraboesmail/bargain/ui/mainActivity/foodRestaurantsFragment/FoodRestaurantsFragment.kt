package com.omaraboesmail.bargain.ui.mainActivity.foodRestaurantsFragment


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.omaraboesmail.bargain.R

class FoodRestaurantsFragment : Fragment() {

    companion object {
        fun newInstance() =
            FoodRestaurantsFragment()
    }

    private val viewModel: FoodRestaurantsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.food_restorants_fragment, container, false)
    }

}
