package com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.order.ProfileViewModel

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        profileViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }
}
