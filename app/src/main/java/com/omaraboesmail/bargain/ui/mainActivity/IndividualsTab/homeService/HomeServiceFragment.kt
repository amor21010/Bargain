package com.omaraboesmail.bargain.ui.mainActivity.IndividualsTab.homeService

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.ui.mainActivity.IndividualsTab.handMadeFragment.IndvidualAdapter

import com.omaraboesmail.bargain.utils.Const
import com.omaraboesmail.bargain.utils.NavigationFlow
import kotlinx.android.synthetic.main.home_service_fragment.*

class HomeServiceFragment : Fragment() {

    companion object {
        fun newInstance() = HomeServiceFragment()
    }

    private val serviceViewModel: HomeServiceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_service_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val warning: TextView = view.findViewById(R.id.longPressWarning2)
        warning.setOnClickListener {
            warning.visibility = View.GONE
        }
        addProduct()
        val recyclerView: RecyclerView = view.findViewById(R.id.individualRecycler)
        val adapter = IndvidualAdapter()
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        serviceViewModel.getAllByType("homeService").observe(viewLifecycleOwner, Observer {
            adapter.swapData(it)
        })
        recyclerView.adapter = adapter

    }

    private fun addProduct() {
        serviceViewModel.getUser().observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                Log.d(Const.TAG, user.toString())
                if (user.isAdmin || user.isSeller) {
                    addNew2.visibility = View.VISIBLE
                    addNew2.setOnClickListener {
                        NavigationFlow(requireContext()).navigateToFragment(R.id.action_homeServiceFragment_to_addIndividualProductFragment)
                    }
                } else addNew2.visibility = View.GONE
            }

        })

    }

}