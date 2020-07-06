package com.omaraboesmail.bargain.ui.mainActivity.homeStoreFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.omaraboesmail.bargain.R

class HomeStoreFragment : Fragment() {

    companion object {
        fun newInstance() = HomeStoreFragment()
    }

    private lateinit var viewModel: HomeStoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_store_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeStoreViewModel::class.java)
        // TODO: Use the ViewModel
    }

}