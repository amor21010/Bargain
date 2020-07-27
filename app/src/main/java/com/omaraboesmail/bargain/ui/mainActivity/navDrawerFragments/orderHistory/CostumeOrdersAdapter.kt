package com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.orderHistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.pojo.CostumeOrder
import com.omaraboesmail.bargain.pojo.DeliveryStat
import java.util.*

class CostumeOrdersAdapter : RecyclerView.Adapter<CostumeOrdersAdapter.CostumeOrderVH>() {

    private var data: List<CostumeOrder> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CostumeOrderVH {
        return CostumeOrderVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.costume_order_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CostumeOrderVH, position: Int) =
        holder.bind(data[position])

    fun swapData(data: List<CostumeOrder>) {
        this.data = data
        notifyDataSetChanged()
    }

    class CostumeOrderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CostumeOrder) = with(itemView) {
            val state: TextView = findViewById(R.id.state)
            val order: TextView = findViewById(R.id.order)
            val time: TextView = findViewById(R.id.time)
            val image: ImageView = findViewById(R.id.stateImage)
            Glide.with(itemView).load(
                when (item.state) {
                    DeliveryStat.WAITING -> R.drawable.ic_stopwatch
                    DeliveryStat.SHIPPED -> R.drawable.ic_on_delivery
                    DeliveryStat.DELIVERED -> R.drawable.ic_done
                    DeliveryStat.CANCELED -> R.drawable.ic_canceled
                }
            ).into(image)
            state.text = item.state.msg
            order.text = item.order
            time.text = item.time.toString()


        }
    }
}