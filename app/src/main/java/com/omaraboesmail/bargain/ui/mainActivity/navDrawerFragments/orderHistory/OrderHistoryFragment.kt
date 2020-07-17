package com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.orderHistory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.utils.Const.TAG

class OrderHistoryFragment : Fragment() {

    private val orderHistoryViewModel: OrderHistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_order_history, container, false)
        val textView: TextView = root.findViewById(R.id.text_history)
        orderHistoryViewModel.getUser()?.email?.let { email ->
            orderHistoryViewModel.getOrderHistory(email)
                .observe(viewLifecycleOwner, Observer { orderList ->
                    Log.d(TAG, "onCreateView: order =$orderList")
                })
            orderHistoryViewModel.getCostumeOrdersHistory(email).observe(viewLifecycleOwner,
                Observer {
                    Log.d(TAG, "onCreateView: costume =$it")
                })

        }
        return root
    }
}
