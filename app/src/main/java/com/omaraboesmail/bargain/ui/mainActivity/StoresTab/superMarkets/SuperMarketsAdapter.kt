package com.omaraboesmail.bargain.ui.mainActivity.StoresTab.superMarkets

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.stores.SuperMarketRepo
import com.omaraboesmail.bargain.pojo.SuperMarket

import com.omaraboesmail.bargain.utils.DiffUtils
import com.omaraboesmail.bargain.utils.NavigationFlow
import kotlinx.android.synthetic.main.super_market_item.view.*
import java.util.*

class SuperMarketsAdapter : RecyclerView.Adapter<SuperMarketsAdapter.SuperMarketVH>() {


    lateinit var mContext: Context
    var data: List<SuperMarket> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperMarketVH {
        return SuperMarketVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.super_market_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: SuperMarketVH, position: Int) {


        val item = data[position]
        holder.bind(item)
        SuperMarketRepo.setMarketName("")
        holder.itemView.setOnClickListener {
            SuperMarketRepo.setMarketName(item.name)
            NavigationFlow(mContext).navigateToFragment(R.id.action_nav_super_market_to_super_market_details)
        }
    }

    fun swapData(data: List<SuperMarket>, context: Context) {
        mContext = context
        val diffCallback = DiffUtils(this.data, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.data = data
        diffResult.dispatchUpdatesTo(this)
    }

    class SuperMarketVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: SuperMarket) = with(itemView) {
            itemView.SuperMarketText.text = item.name
            if (item.photo.isNotEmpty() && item.photo != "")
                Glide.with(context).load(item.photo).into(SuperMarketImage)
            else {
                Glide.with(context).load(context.getDrawable(R.drawable.supermarket_image))
                    .into(SuperMarketImage)
            }


            setOnClickListener {

            }
        }
    }
}