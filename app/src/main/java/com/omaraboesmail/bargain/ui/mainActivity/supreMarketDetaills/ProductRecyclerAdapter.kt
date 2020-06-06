package com.omaraboesmail.bargain.ui.mainActivity.supreMarketDetaills

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.utils.Const.TAG
import java.util.*

class ProductRecyclerAdapter : RecyclerView.Adapter<ProductRecyclerAdapter.ProductVH>() {

    private var data: List<Int> = ArrayList()

    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        return ProductVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        holder.bind(data[position])
        holder.quantity.maxValue = 10
        holder.quantity.minValue = 0


    }


    fun swapData(data: List<Int>, context: Context) {
        this.context = context
        this.data = data
        notifyDataSetChanged()
    }

    class ProductVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quantity = itemView.findViewById<NumberPicker>(R.id.quantity)
        val expandBtn = itemView.findViewById<FloatingActionButton>(R.id.add_fap)

        fun bind(item: Int) = with(itemView) {
            // TODO: Bind the data with View
            setOnClickListener {
                // TODO: Handle on click
                Log.d(TAG, "clicked")


            }
        }
    }
}