package com.omaraboesmail.bargain.ui.mainActivity.cartFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.CartRepo
import com.omaraboesmail.bargain.data.normalOrdersRepo.OrderRepo
import com.omaraboesmail.bargain.pojo.Order
import com.omaraboesmail.bargain.pojo.Product
import com.omaraboesmail.bargain.resultStats.DbCRUDState

class CartViewModel : ViewModel() {
    fun getOnlineCart(email: String) = CartRepo.getOnlineCart(email)
    fun updateCart(product: List<Product>) =
        CartRepo.updateOnlineCart(product = product)

    fun placeOrder(order: Order) = OrderRepo.createOrder(order)
    val insertOrderState: LiveData<DbCRUDState> = OrderRepo.dbCRUDState
}