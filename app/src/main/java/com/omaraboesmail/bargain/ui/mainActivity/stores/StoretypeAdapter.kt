package com.omaraboesmail.bargain.ui.mainActivity.stores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.pojo.StoreType
import com.omaraboesmail.bargain.utils.NavigationFlow
import kotlinx.android.synthetic.main.store_type_item.view.*
import java.util.*

class StoretypeAdapter : RecyclerView.Adapter<StoretypeAdapter.StoretypeVH>() {

    private var data: List<StoreType> = ArrayList()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoretypeVH {
        return StoretypeVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.store_type_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: StoretypeVH, position: Int) {


        val storeType = data[position]
        holder.bind(storeType)
        holder.itemView.setOnClickListener {
            NavigationFlow(context).navigateToFragment(storeType.id)
        }

    }

    fun swapData(data: List<StoreType>, context: Context) {
        this.context = context
        this.data = data
        notifyDataSetChanged()
    }

    class StoretypeVH(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(item: StoreType) = with(itemView) {
            this.storeTypeImage.setImageDrawable(item.drawable)
            this.storeTypeText.text = item.name
            Glide.with(this).load(item.drawable).into(storeTypeImage)
            setOnClickListener {


            }
        }
    }


}