package com.omaraboesmail.bargain.ui.mainActivity.individuals

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
import com.omaraboesmail.bargain.ui.mainActivity.stores.StoretypeAdapter
import kotlinx.android.synthetic.main.individuals_fragment.*

class IndividualsFragment : Fragment() {

    companion object {
        fun newInstance() =
            IndividualsFragment()
    }

    private val viewModel: IndividualsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.individuals_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val storetypeAdapter = StoretypeAdapter()
        val data = ArrayList<StoreType>()
        val homeStore = StoreType(
            R.id.action_nav_individuals_to_homeStoreFragment,
            getString(R.string.homeStore), ContextCompat.getDrawable(
                requireContext(), (R.drawable.home_store)
            )!!
        )
        val handMade = StoreType(
            R.id.action_nav_individuals_to_handMadeFragment,
            this.getString(R.string.handMade), ContextCompat.getDrawable(
                requireContext(), (R.drawable.home_made)
            )!!
        )
        val homeService = StoreType(
            R.id.action_nav_individuals_to_homeServiceFragment,
            this.getString(R.string.homeService), ContextCompat.getDrawable(
                requireContext(), (R.drawable.home_service)
            )!!
        )
        if (data.isEmpty()) {
            data.add(handMade)
            data.add(homeStore)
            data.add(homeService)
        }
        storetypeAdapter.swapData(data, this.requireContext())
        storeTypeRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        storeTypeRecyclerView.adapter = storetypeAdapter


    }


}
