package com.omaraboesmail.bargain.ui.mainActivity.customOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.omaraboesmail.bargain.R

class CustomOrderFragment : Fragment() {

    companion object {
        fun newInstance() = CustomOrderFragment()
    }

    private val viewModel: CostomeOrderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hand_work_fragment, container, false)
    }

}
