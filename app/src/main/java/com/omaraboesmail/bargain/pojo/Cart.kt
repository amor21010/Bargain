package com.omaraboesmail.bargain.pojo

import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.utils.checkItemsAre

data class Cart(
    val email: String = UserRepo.fbUserLive.value?.email!!,
    var products: ArrayList<Product>
)


fun Cart.getSize() = this.products.size

fun Map<String, Any>.toCartObject(): Cart {
    if (this["products"] != null) {
        val productArray = ArrayList<Product>()
        val array = (this["products"] as ArrayList<*>).checkItemsAre<Map<String, String>>()
        for (map in array) productArray.add(map.toProduct())
        if (!array.isNullOrEmpty())
            return Cart(
                email = this["email"].toString(),
                products = productArray
            )
    }
    return Cart(
        email = this["email"].toString(),
        products = arrayListOf()

    )
}

