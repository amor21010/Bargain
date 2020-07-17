package com.omaraboesmail.bargain.ui.mainActivity.StoresTab.restaurantDetailsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.pojo.Restaurant
import com.omaraboesmail.bargain.pojo.RestaurantProduct
import com.omaraboesmail.bargain.pojo.RestaurantProductCategory
import com.omaraboesmail.bargain.pojo.getProductsList
import com.omaraboesmail.bargain.utils.NavigationFlow

class RestaurantDetailsFragment : Fragment() {
    private val productList = ArrayList<RestaurantProduct>()

    companion object {
        fun newInstance() =
            RestaurantDetailsFragment()
    }

    private val viewModel: RestaurantDetailsViewModel by viewModels()

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.retaurant_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.products)

        viewModel.getRestaurant().observe(viewLifecycleOwner, Observer { store ->

            if (store.name == viewModel.getCachedName()) {
                val restaurant = store as Restaurant
                (restaurant).getProductsList().observe(viewLifecycleOwner, Observer {
                    productList.addAll(it)
                    setRecyclerData(restaurant.name)
                })
            }
        })

        NavigationFlow(view.context).onFragmentBackPressed(R.id.nav_resturant)
    }

    private fun setRecyclerData(restaurantName: String) {

        val recyclerAdapter = RestaurantProductsCategoryAdapter()
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val categoryList = ArrayList<RestaurantProductCategory>()


        productList.distinct()

        for (product in productList) {
            val category = RestaurantProductCategory(product.type, emptyList())
            if (!categoryList.contains(category))
                categoryList.add(category)
        }


        for (category in categoryList) {
            val finalList = ArrayList<RestaurantProduct>()
            for (product in productList)
                if (product.type == category.name) {
                    finalList.add(product)

                }
            category.product = finalList.distinct()
        }
        recyclerAdapter.swapData(restaurantName, categoryList.distinct(), requireContext())
        recyclerView.adapter = recyclerAdapter

    }
}