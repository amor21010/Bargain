package com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.myOrders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.omaraboesmail.bargain.R

class MyOrdersFragment : Fragment() {

    private val myOrdersViewModel: MyOrdersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_my_orders, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        myOrdersViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
