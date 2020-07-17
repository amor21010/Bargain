package com.omaraboesmail.bargain.ui.mainActivity.StoresTab.supreMarketDetaills

import android.content.Context
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.pojo.SuperMarketProductCategory
import com.omaraboesmail.bargain.pojo.SupermarketProduct
import com.omaraboesmail.bargain.pojo.getPhotoUri
import com.omaraboesmail.bargain.utils.Const.TAG
import com.omaraboesmail.bargain.utils.checkItemsAre

class ProductsCategoryAdapter :
    RecyclerView.Adapter<ProductsCategoryAdapter.SuperMarketProductsVH>() {

    private var data: List<SuperMarketProductCategory> = ArrayList()


    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperMarketProductsVH {
        return SuperMarketProductsVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_category_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: SuperMarketProductsVH, position: Int) {

        val category = data[position]
        holder.bind(category)

    }

    fun swapData(

        data: List<SuperMarketProductCategory>,
        context: Context
    ) {

        this.context = context
        this.data = data
        notifyDataSetChanged()
    }


    class SuperMarketProductsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView: RecyclerView = itemView.findViewById(R.id.productRecycler)
        val expandable: RelativeLayout = itemView.findViewById(R.id.expandable)
        val productName: TextView = itemView.findViewById(R.id.productName)
        val rootCard: CardView = itemView.findViewById(R.id.rootCard)
        val expandBtn: FloatingActionButton = itemView.findViewById(R.id.expandBtn)
        val imageView: ImageView = itemView.findViewById(R.id.productImage)

        fun bind(category: SuperMarketProductCategory) = with(itemView) {
            val productRecyclerAdapter = ProductRecyclerAdapter()
            (category.product.checkItemsAre<SupermarketProduct>()).let {
                productRecyclerAdapter.swapData(
                    it,
                    context
                )
            }
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = productRecyclerAdapter
            productName.text = category.name
            category.product[0].getPhotoUri().observe(context as LifecycleOwner, Observer {
                if (it != "")
                    Glide.with(context).load(it).into(imageView)
            })

            expandBtn.setOnClickListener {
                Log.d(TAG, "clicked")
                expand()
            }

            setOnClickListener {
                expand()
            }
        }

        fun expand() {
            if (expandable.visibility == View.GONE) {
                Log.d(TAG, "Expanding")
                TransitionManager.beginDelayedTransition(rootCard, AutoTransition())
                expandable.visibility = View.VISIBLE
                Glide.with(itemView).load(R.drawable.ic_shrink).into(expandBtn)
            } else {
                Log.d(TAG, "shrinking")

                expandable.visibility = View.GONE
                Glide.with(itemView).load(R.drawable.ic_expand).into(expandBtn)
            }
        }
    }
}