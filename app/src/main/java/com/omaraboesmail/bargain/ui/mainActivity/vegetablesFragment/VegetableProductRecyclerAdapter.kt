package com.omaraboesmail.bargain.ui.mainActivity.vegetablesFragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.CartRepo
import com.omaraboesmail.bargain.pojo.VegetableProduct
import com.omaraboesmail.bargain.utils.ToastMaker
import java.util.*

class VegetableProductRecyclerAdapter :
    RecyclerView.Adapter<VegetableProductRecyclerAdapter.ProductVH>() {

    private var data: List<VegetableProduct> = ArrayList()

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
    }


    fun swapData(data: List<VegetableProduct>, context: Context) {
        this.context = context
        this.data = data
        notifyDataSetChanged()
    }

    class ProductVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /*   val quantity: NumberPicker = itemView.findViewById(R.id.quantity)*/
        val name: TextView = itemView.findViewById(R.id.name)
        val unit: TextView = itemView.findViewById(R.id.unit)
        val price: TextView = itemView.findViewById(R.id.price)

        /*    val totalPrice: TextView = itemView.findViewById(R.id.totalPrice)*/
        val addFab: FloatingActionButton = itemView.findViewById(R.id.add_fap)


        @SuppressLint("SetTextI18n")
        fun bind(product: VegetableProduct) = with(itemView) {

            name.text = product.name
            unit.text = product.unit
            price.text = "X ${product.price}"

            /* quantity.maxValue = product.quantityAvailable
             quantity.minValue = 0
             quantity.setOnValueChangedListener { _, _, newVal ->
                 getTotalPrice(newVal)
             }*/

            addFab.setOnClickListener {
                /* var newValue = quantity.value
                 if (newValue == quantity.maxValue) return@setOnClickListener

                 addFab.setImageDrawable(
                     ResourcesCompat.getDrawable(
                         itemView.context.resources,
                         R.drawable.ic_add,
                         null
                     )
                 )
                 newValue += 1
                 quantity.value = newValue
                 getTotalPrice(newValue)*/
                // todo update cart

                CartRepo.addToCart(product)
                ToastMaker(this.context, "product Added to Cart Successfully")
                addFab.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        itemView.context.resources,
                        R.drawable.ic_add,
                        null
                    )
                )
            }

        }


        /*private fun getTotalPrice(quantity: Int) {
            if (quantity > 0) {
                totalPrice.visibility = View.VISIBLE
                totalPrice.text =
                    ((price.text.toString().removePrefix("X ").toDouble()) * quantity).toString()

            } else {
                totalPrice.visibility = View.GONE
                addFab.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        itemView.context.resources,
                        R.drawable.ic_shopper,
                        null
                    )
                )
            }
        }*/
    }

}