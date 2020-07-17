package com.omaraboesmail.bargain.ui.mainActivity.cartFragment

import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.CartRepo
import com.omaraboesmail.bargain.data.orders.OrderRepo
import com.omaraboesmail.bargain.pojo.Order
import com.omaraboesmail.bargain.pojo.Product

class CartViewModel : ViewModel() {
    fun getOnlineCart(email: String) = CartRepo.getOnlineCart(email)
    fun updateCart(product: List<Product>) =
        CartRepo.updateOnlineCart(product = product as ArrayList<Product>)

    fun placeOrder(order: Order) = OrderRepo.createOrder(order)
}