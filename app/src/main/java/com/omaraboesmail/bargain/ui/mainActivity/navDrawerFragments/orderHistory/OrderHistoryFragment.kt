package com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.orderHistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.omaraboesmail.bargain.R

class OrderHistoryFragment : Fragment() {

    private val orderHistoryViewModel: OrderHistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_order_history, container, false)
        val textView: TextView = root.findViewById(R.id.text_history)
        orderHistoryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
