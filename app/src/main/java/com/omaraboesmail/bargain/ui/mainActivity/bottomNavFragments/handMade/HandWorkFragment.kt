package com.omaraboesmail.bargain.ui.mainActivity.bottomNavFragments.handMade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.omaraboesmail.bargain.R

class HandWorkFragment : Fragment() {

    companion object {
        fun newInstance() = HandWorkFragment()
    }

    private val viewModel: HandWorkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hand_work_fragment, container, false)
    }

}
