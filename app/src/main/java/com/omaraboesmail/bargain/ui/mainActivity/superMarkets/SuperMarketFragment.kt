package com.omaraboesmail.bargain.ui.mainActivity.superMarkets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.pojo.SuperMarket
import com.omaraboesmail.bargain.utils.NavigationFlow
import com.omaraboesmail.bargain.utils.ToastMaker
import kotlinx.android.synthetic.main.super_market_fragment.*

class SuperMarketFragment : Fragment() {

    companion object {
        fun newInstance() =
            SuperMarketFragment()
    }

    private val viewModel: SuperMarketViewModel by viewModels()
    private var data = ArrayList<SuperMarket>()
    private val adapter = SuperMarketsAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.super_market_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        NavigationFlow(this.requireContext()).onFragmentBackPressed(R.id.nav_store)
        viewModel.getAllSuperMarkets().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                data = (it as ArrayList<SuperMarket>)
                adapter.swapData(data, requireContext())
            } else ToastMaker(requireContext(), "null")
        })
        superMarketRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        superMarketRecycler.adapter = adapter
        addSuperMarket()
    }

    fun addSuperMarket() {
        viewModel.getUser().observe(viewLifecycleOwner, Observer { user ->
            if (user.isAdmin) {
                addMarket.visibility = View.VISIBLE
                addMarket.setOnClickListener {
                    NavigationFlow(requireContext()).navigateToFragment(R.id.action_nav_super_market_to_addSuperMarketFragment)
                }
            } else addMarket.visibility = View.GONE

        })
    }


}
