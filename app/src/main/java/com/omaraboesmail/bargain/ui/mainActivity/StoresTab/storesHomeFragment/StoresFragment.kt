package com.omaraboesmail.bargain.ui.mainActivity.StoresTab.storesHomeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.pojo.StoreType
import com.omaraboesmail.bargain.utils.NavigationFlow
import kotlinx.android.synthetic.main.store_fragment.*
import java.util.*

class StoresFragment : Fragment() {
    private var data: MutableList<StoreType> = ArrayList()

    companion object {
        fun newInstance() =
            StoresFragment()
    }

    private val viewModel: StoresViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.store_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavigationFlow(this.requireContext()).onFragmentBackPressed(R.id.nav_home)

        val storetypeAdapter = StoretypeAdapter()

        val vagMarket = StoreType(
            R.id.action_nav_store_to_vegtablesFragment,
            getString(R.string.vegetables_markets), ContextCompat.getDrawable(
                requireContext(), (R.drawable.vag_market_image)
            )!!
        )
        val superMarket = StoreType(
            R.id.action_nav_store_to_superMarketFragment,
            this.getString(R.string.super_markets), ContextCompat.getDrawable(
                requireContext(), (R.drawable.supermarket_image)
            )!!
        )
        val fastFoodMarket = StoreType(
            R.id.action_nav_store_to_foodRestaurantsFragment,
            this.getString(R.string.restaurants), ContextCompat.getDrawable(
                requireContext(), (R.drawable.food_markets)
            )!!
        )
        if (data.size == 0) {
            data.add(fastFoodMarket)
            data.add(superMarket)
            data.add(vagMarket)
        }
        storetypeAdapter.swapData(data, this.requireContext())
        storeTypeRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        storeTypeRecyclerView.adapter = storetypeAdapter

    }


}
