package com.omaraboesmail.bargain.ui.mainActivity.supreMarketDetaills

import android.content.Context
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.utils.Const.TAG
import java.util.*

class SuperMarketproductsAdapter :
    RecyclerView.Adapter<SuperMarketproductsAdapter.SuperMarketProductsVH>() {

    private var data: List<Int> = ArrayList()
    var productData: List<Int> = ArrayList()
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperMarketProductsVH {
        return SuperMarketProductsVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_category_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: SuperMarketProductsVH, position: Int) {
        holder.bind(data[position])
        val productRecyclerAdapter = ProductRecyclerAdapter()
        productRecyclerAdapter.swapData(productData, context)
        holder.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.recyclerView.adapter = productRecyclerAdapter
        holder.expandBtn.setOnClickListener {
            Log.d(TAG, "clicked")

            if (holder.expandable.visibility == View.GONE) {
                Log.d(TAG, "Expanding")
                TransitionManager.beginDelayedTransition(holder.rootCard, AutoTransition())
                holder.expandable.visibility = View.VISIBLE
                Glide.with(context).load(R.drawable.ic_shrink).into(holder.expandBtn)
            } else {
                Log.d(TAG, "shrinking")

                holder.expandable.visibility = View.GONE
                Glide.with(context).load(R.drawable.ic_expand).into(holder.expandBtn)
            }
        }

    }

    fun swapData(data: List<Int>, productData: List<Int>, context: Context) {
        this.context = context
        this.productData = productData
        this.data = data
        notifyDataSetChanged()
    }

    class SuperMarketProductsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView = itemView.findViewById<RecyclerView>(R.id.productRecycler)
        val expandable = itemView.findViewById<RelativeLayout>(R.id.expandable)
        val root = itemView.findViewById<RelativeLayout>(R.id.rootRecycler)
        val rootCard = itemView.findViewById<CardView>(R.id.rootCard)
        val expandBtn = itemView.findViewById<FloatingActionButton>(R.id.expandBtn)
        fun bind(item: Int) = with(itemView) {
            // TODO: Bind the data with View

            setOnClickListener {
                if (expandable.visibility == View.GONE) {
                    Log.d(TAG, "Expanding")
                    TransitionManager.beginDelayedTransition(rootCard, AutoTransition())
                    expandable.visibility = View.VISIBLE
                    Glide.with(context).load(R.drawable.ic_shrink).into(expandBtn)
                } else {
                    Log.d(TAG, "shrinking")

                    expandable.visibility = View.GONE
                    Glide.with(context).load(R.drawable.ic_expand).into(expandBtn)
                }
            }
        }
    }
}