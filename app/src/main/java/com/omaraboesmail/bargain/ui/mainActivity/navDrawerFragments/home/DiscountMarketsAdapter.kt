package com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.stores.SuperMarketRepo
import com.omaraboesmail.bargain.pojo.SuperMarket
import com.omaraboesmail.bargain.utils.NavigationFlow
import kotlinx.android.synthetic.main.super_market_item.view.*
import java.util.*

class DiscountMarketsAdapter : RecyclerView.Adapter<DiscountMarketsAdapter.DiscountMarketsVH>() {

    private var data: List<SuperMarket> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountMarketsVH {
        return DiscountMarketsVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.super_market_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: DiscountMarketsVH, position: Int) =
        holder.bind(data[position])

    fun swapData(data: List<SuperMarket>) {
        this.data = data
        notifyDataSetChanged()
    }

    class DiscountMarketsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(item: SuperMarket) = with(itemView) {
            if (item.discount != 0f) {
                SuperMarketDiscount.visibility = View.VISIBLE
                SuperMarketDiscount.text = "${item.discount} %"
            }
            SuperMarketText.text = item.name
            Glide.with(context).load(item.photo).into(SuperMarketImage)
            setOnClickListener {
                SuperMarketRepo.setMarketName(item.name)
                NavigationFlow(context).navigateToFragment(R.id.superMarketDetailsFragment)
            }
        }
    }
}