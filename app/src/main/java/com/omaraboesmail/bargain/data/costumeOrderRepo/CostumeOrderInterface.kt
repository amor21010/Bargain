package com.omaraboesmail.bargain.data.costumeOrderRepo

import androidx.lifecycle.LiveData
import com.omaraboesmail.bargain.pojo.CostumeOrder

interface CostumeOrderInterface {
    fun getAllUserOrder(email: String): LiveData<List<CostumeOrder>>
    fun createOrder(order: String)
}