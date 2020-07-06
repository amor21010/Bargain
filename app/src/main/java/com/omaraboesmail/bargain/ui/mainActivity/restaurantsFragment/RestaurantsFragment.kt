package com.omaraboesmail.bargain.ui.mainActivity.restaurantsFragment


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
import com.omaraboesmail.bargain.utils.NavigationFlow
import com.omaraboesmail.bargain.utils.checkItemsAre

class RestaurantsFragment : Fragment() {

    companion object {
        fun newInstance() =
            RestaurantsFragment()
    }

    private val viewModel: RestaurantsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.restorants_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val restaurantAdapter = RestaurantAdapter()
        NavigationFlow(view.context).onFragmentBackPressed(R.id.nav_store)

        val restaurantRecyclerView: RecyclerView = view.findViewById(R.id.restaurantRecyclerView)
        restaurantRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        restaurantRecyclerView.adapter = restaurantAdapter
        viewModel.getAllRestaurants().observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {

                restaurantAdapter.swapData(it.checkItemsAre<Restaurant>(), requireContext())

            }
        })
    }

}
