package com.omaraboesmail.bargain.ui.mainActivity.IndividualsTab.handMadeFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.CartRepo
import com.omaraboesmail.bargain.pojo.IndividualProduct
import com.omaraboesmail.bargain.pojo.getPhotoUri
import java.util.*

class IndvidualAdapter : RecyclerView.Adapter<IndvidualAdapter.IndividualVH>() {

    private var data: List<IndividualProduct> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndividualVH {
        return IndividualVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.individual_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: IndividualVH, position: Int) {
        holder.bind(data[position])
    }

    fun swapData(data: List<IndividualProduct>) {
        this.data = data
        notifyDataSetChanged()
    }

    class IndividualVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: IndividualProduct) = with(itemView) {
            val imageView: ImageView = findViewById(R.id.productImage)
            val name: TextView = findViewById(R.id.productName)
            val seller: TextView = findViewById(R.id.seller)
            val price: TextView = findViewById(R.id.price)

            val ratingBar: RatingBar = findViewById(R.id.rating)
            name.text = item.name
            seller.text = item.seller
            price.text = "${item.price} L.E"
            if (item.review != 0f) {
                ratingBar.visibility = View.VISIBLE
                ratingBar.rating = item.review
            } else ratingBar.visibility = View.GONE

            setOnClickListener {
                CartRepo.addToCart(item)
            }
            item.getPhotoUri().observe(context as LifecycleOwner, androidx.lifecycle.Observer {
                Glide.with(this).load(it).into(imageView)

            })

            setOnLongClickListener {
                //todo show dialog with item details and reviews
                return@setOnLongClickListener true
            }
        }
    }
}