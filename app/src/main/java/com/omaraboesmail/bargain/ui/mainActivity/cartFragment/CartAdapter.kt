package com.omaraboesmail.bargain.ui.mainActivity.cartFragment

import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.CartRepo
import com.omaraboesmail.bargain.pojo.Product
import com.omaraboesmail.bargain.pojo.getPhotoUri

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartVH>() {

    private var data: ArrayList<Product> = ArrayList()
    private val viewModel: CartViewModel = CartViewModel()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        return CartVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cart_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CartVH, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.quantity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(it: Editable?) {
                if (!it.isNullOrEmpty() && it.isDigitsOnly()) {
                    if (it.toString().toInt() == item.quantityOrdered) return
                    item.quantityOrdered = it.toString().toInt()
                    viewModel.updateCart(data)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty() || p0.isDigitsOnly() || p0.toString()
                        .toInt() == item.quantityOrdered
                ) return
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

    }

    fun swapData(data: ArrayList<Product>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        data.removeAt(position)
        CartRepo.updateOnlineCart(data)
    }

    class CartVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.name)
        private val unit: TextView = itemView.findViewById(R.id.unit)
        private val price: TextView = itemView.findViewById(R.id.price)
        val quantity: EditText = itemView.findViewById(R.id.quantity)
        private val addFap: FloatingActionButton = itemView.findViewById(R.id.plus)
        private val minusFap: FloatingActionButton = itemView.findViewById(R.id.minus)
        private val image: ImageView = itemView.findViewById(R.id.image)
        fun bind(item: Product) = with(itemView) {
            name.text = item.name
            unit.text = item.unit
            price.text = item.price
            item.getPhotoUri().observe(context as LifecycleOwner, Observer {
                if (!it.isNullOrEmpty()) {
                    Glide.with(itemView.context).load(it).into(image)
                } else Glide.with(itemView.context).load(R.drawable.food_markets).into(image)


            })


            quantity.setText("${item.quantityOrdered}")
            if (item.quantityOrdered <= 1) disableMinusFab()




            addFap.setOnClickListener {
                if (quantity.text.isNullOrEmpty() && quantity.text.isDigitsOnly()) return@setOnClickListener
                else {
                    val old = quantity.text.toString().toInt()
                    val newString = old + 1
                    quantity.setText("$newString")


                }
                if (!minusFap.isEnabled) enableMinusFab()
            }
            minusFap.setOnClickListener {
                if (quantity.text.isNullOrEmpty() && quantity.text.isDigitsOnly()) return@setOnClickListener
                else {
                    val old = quantity.text.toString().toInt()
                    if (old > 1) {
                        val newString = old - 1
                        quantity.setText("$newString")


                        if (newString == 1) {
                            disableMinusFab()
                        }
                    }
                }


            }
        }

        private fun disableMinusFab() {
            minusFap.isEnabled = false
            minusFap.backgroundTintList =
                ColorStateList.valueOf(getColor(itemView.context, R.color.colorAccentLight))

        }

        private fun enableMinusFab() {
            minusFap.isEnabled = true
            minusFap.backgroundTintList =
                ColorStateList.valueOf(getColor(itemView.context, R.color.colorAccent))
        }


    }

}


