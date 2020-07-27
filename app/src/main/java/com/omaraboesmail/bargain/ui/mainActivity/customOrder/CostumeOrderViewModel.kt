package com.omaraboesmail.bargain.ui.mainActivity.customOrder

import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.costumeOrderRepo.CostumeOrderRepo

class CostumeOrderViewModel : ViewModel() {
    fun makeOrder(order: String) = CostumeOrderRepo.createOrder(order)
    val orderState = CostumeOrderRepo.orderStat
}
