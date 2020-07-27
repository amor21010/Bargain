package com.omaraboesmail.bargain.data.normalOrdersRepo

import androidx.lifecycle.LiveData
import com.omaraboesmail.bargain.pojo.Order

interface OrderInterface {
    fun getAllUserOrder(email: String): LiveData<List<Order>>
    fun createOrder(order: Order)
}