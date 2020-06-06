package com.omaraboesmail.bargain.ui.mainActivity.supreMarketDetaills

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.utils.Const.TAG
import kotlinx.android.synthetic.main.super_market_details_fragment.*

class SuperMarketDetailsFragment : Fragment() {

    val superMarketproductsAdapter: SuperMarketproductsAdapter = SuperMarketproductsAdapter()


    companion object {
        fun newInstance() = SuperMarketDetailsFragment()
    }

    private val viewModel: SuperMarketDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.super_market_details_fragment, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        superMarketproductsAdapter.swapData(
            arrayListOf(1, 2, 3, 4, 5, 6),
            arrayListOf(2, 3, 4, 5, 6),
            requireContext()
        )
        products.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        products.adapter = superMarketproductsAdapter
        settingToolBar()
        // TODO: Use the ViewModel
    }

    fun settingToolBar() {
        viewModel.getClickedMarket().observe(viewLifecycleOwner, Observer {
            requireView().invalidate()
            (activity as AppCompatActivity).setSupportActionBar(null)
            (activity as AppCompatActivity).supportActionBar?.title = it.name
            phone.text = it.phone
            address.text = it.address
            superMarketToolbar.title = viewModel.getCachedMarketName()
            (activity as AppCompatActivity).setSupportActionBar(superMarketToolbar)
            Log.d(
                TAG,
                (activity as AppCompatActivity).supportActionBar?.title.toString() + "\n" + superMarketToolbar.title + "\n" + it.name
            )

        })
    }

}
