package com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.orderHistory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.pojo.DeliveryStat
import com.omaraboesmail.bargain.pojo.Order
import java.util.*

class NormalOrdersAdapter : RecyclerView.Adapter<NormalOrdersAdapter.NormalOrdersVH>() {

    private var data: List<Order> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NormalOrdersVH {
        return NormalOrdersVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.order_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: NormalOrdersVH, position: Int) =
        holder.bind(data[position])

    fun swapData(data: List<Order>) {
        this.data = data
        notifyDataSetChanged()
    }

    class NormalOrdersVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Order) = with(itemView) {
            val state: TextView = findViewById(R.id.state)
            val order: TextView = findViewById(R.id.order)
            val time: TextView = findViewById(R.id.time)
            val price: TextView = findViewById(R.id.orderPrice)
            val seller: TextView = findViewById(R.id.orderSellers)
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
            item.cart.products.forEach {
                order.text =
                    when {
                        order.text.isNullOrEmpty() -> it.name
                        order.text.contains(it.name) -> return@forEach
                        else -> {
                            "${order.text} & ${it.name}"
                        }
                    }
                seller.text =
                    when {
                        seller.text.isNullOrEmpty() -> "from : ${it.seller}"
                        seller.text.contains(it.seller) -> return@forEach
                        else -> {
                            "${seller.text} & ${it.seller}"
                        }
                    }
            }
            price.text = "${item.totalPrice} L.E"
            time.text = item.time.toDate().toString()
            setOnClickListener {
                // TODO: Handle on click
            }
        }
    }
}