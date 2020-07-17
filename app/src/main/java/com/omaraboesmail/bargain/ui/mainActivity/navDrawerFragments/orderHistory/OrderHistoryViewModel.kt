package com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.orderHistory

import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.data.orders.OrderRepo
import com.omaraboesmail.bargain.data.orders.costumeOrderRepo.CostumeOrderRepo

class OrderHistoryViewModel : ViewModel() {

    fun getUser() = UserRepo.fbUserLive.value
    fun getOrderHistory(email: String) = OrderRepo.getAllUserOrder(email)
    fun getCostumeOrdersHistory(email: String) = CostumeOrderRepo.getAllUserOrder(email)
}