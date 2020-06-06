package com.omaraboesmail.bargain.ui.mainActivity.vegtablesFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.omaraboesmail.bargain.R

class VegtablesFragment : Fragment() {

    companion object {
        fun newInstance() =
            VegtablesFragment()
    }

    private val viewModel: VegtablesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.vegtables_fragment, container, false)
    }

}
