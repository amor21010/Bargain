package com.omaraboesmail.bargain.ui.mainActivity.StoresTab.restaurantDetailsFragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.CartRepo
import com.omaraboesmail.bargain.pojo.Product
import com.omaraboesmail.bargain.pojo.RestaurantProduct
import com.omaraboesmail.bargain.pojo.getPhotoUri
import java.util.*

class RestaurantProductRecyclerAdapter :
    RecyclerView.Adapter<RestaurantProductRecyclerAdapter.ProductVH>() {
    lateinit var restaurantName: String
    private var data: List<RestaurantProduct> = ArrayList()

    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        return ProductVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.restuarant_product_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ProductVH, position: Int) {

        holder.bind(data[position])
    }


    fun swapData(restaurantName: String, data: List<RestaurantProduct>, context: Context) {
        this.restaurantName = restaurantName
        this.context = context
        this.data = data
        notifyDataSetChanged()
    }

    class ProductVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView = itemView.findViewById(R.id.productName)

        private val price: TextView = itemView.findViewById(R.id.price)
        private val image: ImageView = itemView.findViewById(R.id.productImage)


        @SuppressLint("SetTextI18n")
        fun bind(product: RestaurantProduct) = with(itemView) {
            name.text = product.name
            price.text = " ${product.price} L.E"
            product.getPhotoUri()
                .observe(context as LifecycleOwner, androidx.lifecycle.Observer {
                    if (!it.isNullOrEmpty()) Glide.with(context).load(it).into(image)
                })
            setOnClickListener {
                //todo add to cart
                val product1 = Product(
                    product.name,
                    product.price,
                    "restaurant",
                    product.type,
                    product.seller,
                    product.unit,
                    1
                )
                CartRepo.addToCart(product1, 1)
            }

        }
    }

}