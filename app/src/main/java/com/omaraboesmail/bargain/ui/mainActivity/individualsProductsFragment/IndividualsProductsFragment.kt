package com.omaraboesmail.bargain.ui.mainActivity.individualsProductsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.omaraboesmail.bargain.R

class IndividualsProductsFragment : Fragment() {

    companion object {
        fun newInstance() =
            IndividualsProductsFragment()
    }

    private val viewModel: IndividualsProductsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.individuals_products_fragment, container, false)
    }

}