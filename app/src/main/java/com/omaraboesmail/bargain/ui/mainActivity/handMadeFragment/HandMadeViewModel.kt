package com.omaraboesmail.bargain.ui.mainActivity.handMadeFragment

import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.IndividualsRepo
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.pojo.IndividualProduct

class HandMadeViewModel : ViewModel() {
    fun addProduct(product: IndividualProduct) = IndividualsRepo.insertProduct(product)
    fun getAllByType(type: String) = IndividualsRepo.getAllProductsByType(type)
    fun getUser() = UserRepo.currant
}