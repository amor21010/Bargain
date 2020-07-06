package com.omaraboesmail.bargain.ui.mainActivity

import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.CartRepo
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.pojo.Product

class MainActivityViewModel : ViewModel() {
    fun signOut() = UserRepo.signOut()
    fun getCurrantUserDetails() = UserRepo.currant
    fun getCartSize() = CartRepo.getCartListSize()
    fun addToCart(product: Product, quantity: Int) = CartRepo.addToCart(product, quantity)
    fun getOnlineCart(email: String) = CartRepo.getOnlineCart(email)

}