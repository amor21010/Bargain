package com.omaraboesmail.bargain.ui.mainActivity.StoresTab.vegetablesFragment

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
import com.omaraboesmail.bargain.pojo.VegetableProduct
import com.omaraboesmail.bargain.pojo.VegetableProductCategory

class VegetablesFragment : Fragment() {

    companion object {
        fun newInstance() =
            VegetablesFragment()
    }

    private val viewModel: VegetablesViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    var productList = ArrayList<VegetableProduct>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.vegtables_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.vegetableRecycler)
        viewModel.getVegetableList().observe(viewLifecycleOwner, Observer {
            productList = (it as ArrayList<VegetableProduct>)
            setRecyclerData()
        })
    }

    private fun setRecyclerData() {

        val recyclerAdapter = VegetableProductsCategoryAdapter()
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val categoryList = ArrayList<VegetableProductCategory>()

        arrayListOf("fruit", "vegetable").forEach {
            val productCat = VegetableProductCategory(it, emptyList())
            if (!categoryList.contains(productCat))
                categoryList.add(productCat)
        }

        categoryList.forEach {
            val finalList = ArrayList<VegetableProduct>()
            productList.forEach { product ->
                if (product.type == it.name)
                    finalList.add(product)
            }
            it.product = finalList.distinct()
            finalList.clear()
        }

        recyclerAdapter.swapData(categoryList, requireContext())
        recyclerView.adapter = recyclerAdapter

        productList.clear()
    }

}
