package com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.data.individuals.IndividualsRepo
import com.omaraboesmail.bargain.pojo.IndividualProduct
import com.omaraboesmail.bargain.pojo.getPhotoUri
import com.omaraboesmail.bargain.utils.NavigationFlow
import kotlinx.android.synthetic.main.super_market_item.view.*
import java.util.*

class DiscountIndividualAdapter :
    RecyclerView.Adapter<DiscountIndividualAdapter.DiscountIndividualVH>() {

    private var data: List<IndividualProduct> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountIndividualVH {
        return DiscountIndividualVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.super_market_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: DiscountIndividualVH, position: Int) =
        holder.bind(data[position])

    fun swapData(data: List<IndividualProduct>) {
        this.data = data
        notifyDataSetChanged()
    }

    class DiscountIndividualVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(item: IndividualProduct) = with(itemView) {
            if (item.discount != 0f) {
                SuperMarketDiscount.visibility = View.VISIBLE
                SuperMarketDiscount.text = "${item.discount} %"
            }
            SuperMarketText.text = item.name
            item.getPhotoUri().observe(context as LifecycleOwner, androidx.lifecycle.Observer {
                Glide.with(context).load(it).into(SuperMarketImage)
            })

            setOnClickListener {
                IndividualsRepo.setClickedProduct(item)
                if (UserRepo.currant.value?.name == item.seller) NavigationFlow(context).navigateToFragment(
                    R.id.editProductFragment
                )
            }
        }
    }
}