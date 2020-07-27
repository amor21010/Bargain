package com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.FireBaseConst.firebaseAuthInstance
import com.omaraboesmail.bargain.data.UserRepo.fbUserLive
import com.omaraboesmail.bargain.data.UserRepo.setFirebaseUser
import com.omaraboesmail.bargain.pojo.IndividualProduct
import com.omaraboesmail.bargain.pojo.User
import com.omaraboesmail.bargain.utils.Const.TAG
import com.omaraboesmail.bargain.utils.DialogMaker
import com.omaraboesmail.bargain.utils.setEmail

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    lateinit var v: View
    private val imageList = ArrayList<String>()
    private lateinit var dialog: Dialog
    private lateinit var viewFlipper: ViewFlipper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onResume() {
        getUserData()
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view
        getUserData()
        viewFlipper = view.findViewById(R.id.viewFlipper)

        homeViewModel.getBanners().observe(viewLifecycleOwner, Observer { urlList ->
            urlList.forEach { url ->
                Log.d(TAG, "onViewCreated: $url")
                if (!imageList.contains(url)) {
                    imageList.add(url)
                }
            }
            setBanners()
        })
        createCategoryRecycler()
        createDiscountMarketsRecycler()
        createDiscountIndividualsRecycler()

    }

    private fun setBanners() {
        viewFlipper.setInAnimation(requireContext(), android.R.anim.slide_in_left)
        viewFlipper.setOutAnimation(requireContext(), android.R.anim.slide_out_right)
        for (image in imageList) {
            val imageView = ImageView(requireContext())
            val layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            layoutParams.gravity = Gravity.CENTER
            imageView.layoutParams = layoutParams
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(requireContext()).load(image).into(imageView)
            viewFlipper.addView(imageView)
        }
        viewFlipper.flipInterval = 5000
        viewFlipper.startFlipping()
    }

    @SuppressLint("SetTextI18n")
    private fun getUserData() {

        dialog = DialogMaker.verifyEmailDialog()
        firebaseAuthInstance.currentUser?.reload()
        setFirebaseUser(firebaseAuthInstance.currentUser)
        homeViewModel.getCurrentUserData.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                setEmail(user.email)
                getEmailVerStat(user)
            }
        })
    }


    @SuppressLint("SetTextI18n")
    private fun getEmailVerStat(user: User) {
        homeViewModel.isUserEmailVerified()
            .observe(viewLifecycleOwner, Observer { state ->
                Log.d(TAG, "stata +$state")
                if (!state.state) {
                    dialog.dismiss()
                    dialog.show()
                } else {
                    if (dialog.isShowing) dialog.dismiss()
                    if (user.id != fbUserLive.value!!.uid || user.approved != state.state) {
                        user.id = fbUserLive.value!!.uid
                        user.approved = state.state
                        homeViewModel.updateUser(user = user)
                    }
                }
            })
    }

    fun createCategoryRecycler() {

        val categoryRecycler: RecyclerView = v.findViewById(R.id.categoriesRecycler)
        val categoryAdapter = CategoryAdapter()
        categoryRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        categoryRecycler.adapter = categoryAdapter
        val list = ArrayList<Map<String, Any>>()
        val map = hashMapOf(
            "type" to "super Markets",
            "id" to R.id.nav_super_market
        )
        val map2 = hashMapOf(
            "type" to "hand made",
            "id" to R.id.handMadeFragment
        )
        list.add(map)
        list.add(map2)
        categoryAdapter.swapData(list)
    }

    private fun createDiscountMarketsRecycler() {
        val discountRecyclerView: RecyclerView = v.findViewById(R.id.discountMarketsRecycler)
        val discountAdapter = DiscountMarketsAdapter()
        val textView: TextView = v.findViewById(R.id.SuperMarketDiscountText)
        discountRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        discountRecyclerView.adapter = discountAdapter

        homeViewModel.getSuperMarketsDiscount().observe(viewLifecycleOwner, Observer { markets ->
            textView.visibility = if (!markets.isNullOrEmpty()) View.VISIBLE else View.GONE
            discountAdapter.swapData(markets)
        })
    }

    private fun createDiscountIndividualsRecycler() {
        val discountRecyclerView: RecyclerView = v.findViewById(R.id.discountIndividualRecycler)
        val discountAdapter = DiscountIndividualAdapter()
        val textView: TextView = v.findViewById(R.id.SuperMarketDiscountText)
        discountRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        discountRecyclerView.adapter = discountAdapter
        getIndividualDiscounts().observe(viewLifecycleOwner, Observer { individuals ->
            textView.visibility = if (!individuals.isNullOrEmpty()) View.VISIBLE else View.GONE
            discountAdapter.swapData(individuals)
        })

    }

    private fun getIndividualDiscounts(): LiveData<List<IndividualProduct>> {

        return object : LiveData<List<IndividualProduct>>() {
            override fun onActive() {
                super.onActive()
                val list = ArrayList<IndividualProduct>()
                homeViewModel.getIndividualsDiscount("handMade")
                    .observe(viewLifecycleOwner, Observer { individuals ->
                        list.addAll(individuals)
                        value = list.distinct()

                    })
                homeViewModel.getIndividualsDiscount("homeService")
                    .observe(viewLifecycleOwner, Observer { individuals ->
                        list.addAll(individuals)
                        value = list.distinct()

                    })
                homeViewModel.getIndividualsDiscount("homeStore")
                    .observe(viewLifecycleOwner, Observer { individuals ->
                        list.addAll(individuals)
                        value = list.distinct()
                    })


            }
        }

    }


}


