package com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.orderHistory

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.omaraboesmail.bargain.R

class OrderHistoryFragment : Fragment() {

    private val orderHistoryViewModel: OrderHistoryViewModel by viewModels()
    var isNormalShowed = false
    var isCostumeShowed = false
    lateinit var normalSec: CardView
    lateinit var costumeSec: CardView
    lateinit var costumeRecycler: RecyclerView
    lateinit var normalExpandBtn: FloatingActionButton
    lateinit var costumeExpandBtn: FloatingActionButton
    lateinit var normalRecycler: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_order_history, container, false)
        costumeSec = root.findViewById(R.id.costume_sec)
        normalSec = root.findViewById(R.id.normal_sec)
        costumeRecycler = root.findViewById(R.id.costumeRecycler)
        normalRecycler = root.findViewById(R.id.normalOrderRecycler)
        costumeExpandBtn = root.findViewById(R.id.costumeExpandBtn)
        normalExpandBtn = root.findViewById(R.id.normalExpandBtn)
        showCostumeOrders()
        showNormalOrders()
        getAllOrders()
        return root
    }


    private fun getAllOrders() {

        val costumeOrdersAdapter = CostumeOrdersAdapter()
        val normalOrdersAdapter = NormalOrdersAdapter()
        costumeRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        costumeRecycler.adapter = costumeOrdersAdapter

        normalRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        normalRecycler.adapter = normalOrdersAdapter
        orderHistoryViewModel.getUser()?.email?.let { email ->
            orderHistoryViewModel.getOrderHistory(email)
                .observe(viewLifecycleOwner, Observer { orderList ->
                    normalOrdersAdapter.swapData(orderList)
                })
            orderHistoryViewModel.getCostumeOrdersHistory(email).observe(viewLifecycleOwner,
                Observer { costumeOrders ->
                    costumeOrdersAdapter.swapData(costumeOrders)
                })

        }
    }

    private fun showCostumeOrders() {
        costumeSec.setOnClickListener {
            expand(isCostumeShowed, costumeRecycler, costumeExpandBtn)
            isCostumeShowed = !isCostumeShowed
        }
        costumeExpandBtn.setOnClickListener {
            expand(isCostumeShowed, costumeRecycler, costumeExpandBtn)
            isCostumeShowed = !isCostumeShowed
        }
    }

    private fun showNormalOrders() {

        normalExpandBtn.setOnClickListener {
            expand(isNormalShowed, normalRecycler, normalExpandBtn)
            isNormalShowed = !isNormalShowed
        }
        normalSec.setOnClickListener {
            expand(isNormalShowed, normalRecycler, normalExpandBtn)
            isNormalShowed = !isNormalShowed
        }
    }

    fun expand(
        boolean: Boolean,
        recyclerView: RecyclerView,
        floatingActionButton: FloatingActionButton
    ) {
        if (boolean) {
            Glide.with(requireContext()).load(R.drawable.ic_expand).into(floatingActionButton)
            TransitionManager.beginDelayedTransition(recyclerView, AutoTransition())
            recyclerView.visibility = View.GONE

        } else {
            Glide.with(requireContext()).load(R.drawable.ic_shrink).into(floatingActionButton)
            recyclerView.visibility = View.VISIBLE
        }

    }
}
