package com.omaraboesmail.bargain.ui.mainActivity.supreMarketDetaills

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.pojo.SuperMarket
import com.omaraboesmail.bargain.pojo.SuperMarketProductCategory
import com.omaraboesmail.bargain.pojo.SupermarketProduct
import com.omaraboesmail.bargain.pojo.getProductsList
import com.omaraboesmail.bargain.utils.Const.TAG
import com.omaraboesmail.bargain.utils.NavigationFlow
import kotlinx.android.synthetic.main.super_market_details_fragment.*

class SuperMarketDetailsFragment : Fragment() {

    val ProductsCategoryAdapter: ProductsCategoryAdapter = ProductsCategoryAdapter()

    private var productList = ArrayList<SupermarketProduct>()
    private var categoryList = ArrayList<SuperMarketProductCategory>()

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

        settingToolBar()
        NavigationFlow(requireContext()).onFragmentBackPressed(R.id.nav_super_market)
    }


    private fun settingToolBar() {

        viewModel.getClickedMarket().observe(viewLifecycleOwner, Observer {
            (activity as AppCompatActivity).setSupportActionBar(null)
            (activity as AppCompatActivity).supportActionBar?.title = it.name
            phone.text = it.phone
            address.text = it.address
            superMarketToolbar.title = viewModel.getCachedMarketName()

            (activity as AppCompatActivity).setSupportActionBar(superMarketToolbar)


            if (it.products.isNotEmpty() && it.name == viewModel.getCachedMarketName()) {

                Log.d(TAG, "product:$it\n")
                it.getProducts().observe(viewLifecycleOwner, Observer { productList ->
                    productList.distinct()
                    if (!this.productList.containsAll(productList))
                        this.productList.addAll(productList)
                    if (productList.isNotEmpty())
                        setRecyclerView()
                })
            }
        })
    }

    private fun setRecyclerView() {
        if (productList.isNotEmpty()) {
            productList.distinct()
//add all category names
            for (product in productList) {
                val category = SuperMarketProductCategory(product.type, emptyList())
                if (!categoryList.contains(category))
                    categoryList.add(category)
            }
            categoryList = ArrayList(categoryList.distinct())
//sort category by name

            fun selector(c: SuperMarketProductCategory): String = c.name
            categoryList.sortBy { selector(it) }
//add every product list to it's category
            for (category in categoryList) {
                val finalProductList = ArrayList<SupermarketProduct>()
                for (product in productList) {
                    if (product.type == category.name && !finalProductList.contains(product))
                        finalProductList.add(product)
                    category.product = finalProductList.distinct()
                }
                categoryList[categoryList.lastIndexOf(category)] = SuperMarketProductCategory(
                    category.name,
                    product = finalProductList
                )
            }
        } else {
            categoryList.clear()
        }

        ProductsCategoryAdapter.swapData(
            categoryList.distinct(),
            requireContext()
        )
        products.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        products.adapter = ProductsCategoryAdapter
    }

    private fun SuperMarket.getProducts(): LiveData<List<SupermarketProduct>> {
        return this.getProductsList()
    }

}
