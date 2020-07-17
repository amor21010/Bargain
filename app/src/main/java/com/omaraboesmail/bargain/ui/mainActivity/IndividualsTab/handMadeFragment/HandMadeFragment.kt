package com.omaraboesmail.bargain.ui.mainActivity.IndividualsTab.handMadeFragment

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
import com.omaraboesmail.bargain.utils.Const.TAG
import com.omaraboesmail.bargain.utils.NavigationFlow
import kotlinx.android.synthetic.main.hand_made_fragment.*

class HandMadeFragment : Fragment() {

    companion object {
        fun newInstance() = HandMadeFragment()
    }

    private val viewModel: HandMadeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hand_made_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val warning: TextView = view.findViewById(R.id.longPressWarning)
        warning.setOnClickListener {
            warning.visibility = View.GONE
        }
        val recyclerView: RecyclerView = view.findViewById(R.id.individualRecycler)
        val adapter = IndvidualAdapter()
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        viewModel.getAllByType("handMade").observe(viewLifecycleOwner, Observer {
            adapter.swapData(it)
        })
        recyclerView.adapter = adapter
        addProduct()
    }

    private fun addProduct() {
        viewModel.getUser().observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                Log.d(TAG, user.toString())
                if (user.isAdmin || user.isSeller) {
                    addNew.visibility = View.VISIBLE
                    addNew.setOnClickListener {
                        NavigationFlow(requireContext()).navigateToFragment(R.id.action_handMadeFragment_to_addIndividualProductFragment)
                    }
                } else addNew.visibility = View.GONE
            }

        })

    }
}