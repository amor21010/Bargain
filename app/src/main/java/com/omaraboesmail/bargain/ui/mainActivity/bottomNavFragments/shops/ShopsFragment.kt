package com.omaraboesmail.bargain.ui.mainActivity.bottomNavFragments.shops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.omaraboesmail.bargain.R

class ShopsFragment : Fragment() {

    companion object {
        fun newInstance() =
            ShopsFragment()
    }

    private val viewModel: ShopsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shops_fragment, container, false)
    }

}