package com.omaraboesmail.bargain.ui.mainActivity.restaurantsFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.RestaurantRepo
import com.omaraboesmail.bargain.pojo.Restaurant
import com.omaraboesmail.bargain.pojo.getPhoto
import com.omaraboesmail.bargain.utils.DiffUtils
import com.omaraboesmail.bargain.utils.NavigationFlow
import kotlinx.android.synthetic.main.restaurant_item.view.*
import java.util.*

class RestaurantAdapter : RecyclerView.Adapter<RestaurantAdapter.RestaurantVH>() {


    lateinit var mContext: Context
    var data: List<Restaurant> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantVH {
        return RestaurantVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.restaurant_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RestaurantVH, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    fun swapData(data: List<Restaurant>, context: Context) {
        mContext = context
        val diffCallback = DiffUtils(this.data, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.data = data
        diffResult.dispatchUpdatesTo(this)
    }

    class RestaurantVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Restaurant) = with(itemView) {
            restaurantText.text = item.name
            item.getPhoto()
                .observe(itemView.context as LifecycleOwner, androidx.lifecycle.Observer {
                    if (it != null)
                        Glide.with(context).load(it).into(restaurantImage)
                    else Glide.with(context).load(R.drawable.food_markets).into(restaurantImage)
                })




            setOnClickListener {
                RestaurantRepo.setRestaurantName(item.name)
                NavigationFlow(context).navigateToFragment(R.id.action_nav_resturant_to_restaurantDetailsFragment)
            }
        }
    }
}