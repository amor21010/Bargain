package com.omaraboesmail.bargain.ui.mainActivity.individuals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.omaraboesmail.bargain.R

class IndividualsFragment : Fragment() {

    companion object {
        fun newInstance() =
            IndividualsFragment()
    }

    private val viewModel: IndividualsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.individuals_fragment, container, false)
    }

}
