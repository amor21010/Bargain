package com.omaraboesmail.bargain.ui.mainActivity.StoresTab.restaurantDetailsFragment

import android.content.Context
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.pojo.RestaurantProduct
import com.omaraboesmail.bargain.pojo.RestaurantProductCategory
import com.omaraboesmail.bargain.pojo.getPhotoUri
import com.omaraboesmail.bargain.utils.checkItemsAre

class RestaurantProductsCategoryAdapter :
    RecyclerView.Adapter<RestaurantProductsCategoryAdapter.RestaurantProductsCategoryVH>() {

    private var data: List<RestaurantProductCategory> = ArrayList()
    lateinit var restaurantName: String

    lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantProductsCategoryVH {
        return RestaurantProductsCategoryVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_category_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RestaurantProductsCategoryVH, position: Int) {

        val category = data[position]

        holder.bind(restaurantName, category)

    }

    fun swapData(
        restaurantName: String,
        data: List<RestaurantProductCategory>,
        context: Context
    ) {
        this.restaurantName = restaurantName
        this.context = context
        this.data = data
        notifyDataSetChanged()
    }


    class RestaurantProductsCategoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recyclerView: RecyclerView = itemView.findViewById(R.id.productRecycler)
        private val expandable: RelativeLayout = itemView.findViewById(R.id.expandable)
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val rootCard: CardView = itemView.findViewById(R.id.rootCard)
        private val expandBtn: FloatingActionButton = itemView.findViewById(R.id.expandBtn)
        private val imageView: ImageView = itemView.findViewById(R.id.productImage)

        fun bind(restaurantName: String, category: RestaurantProductCategory) = with(itemView) {
            val productRecyclerAdapter = RestaurantProductRecyclerAdapter()
            (category.product.checkItemsAre<RestaurantProduct>()).let {
                productRecyclerAdapter.swapData(
                    restaurantName,
                    it,
                    context
                )
            }
            recyclerView.layoutManager =
                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            recyclerView.adapter = productRecyclerAdapter
            productName.text = category.name
            category.product[0].getPhotoUri()
                .observe(context as LifecycleOwner, Observer {
                    if (it != "")
                        Glide.with(context).load(it).into(imageView)
                })

            expandBtn.setOnClickListener {

                expand()
            }

            setOnClickListener {
                expand()
            }
        }

        private fun expand() {
            if (expandable.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(rootCard, AutoTransition())
                expandable.visibility = View.VISIBLE
                Glide.with(itemView).load(R.drawable.ic_shrink).into(expandBtn)
            } else {
                expandable.visibility = View.GONE
                Glide.with(itemView).load(R.drawable.ic_expand).into(expandBtn)
            }
        }
    }
}