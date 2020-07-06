package com.omaraboesmail.bargain.pojo

import androidx.lifecycle.LiveData
import com.omaraboesmail.bargain.data.ProductRepo

open class Product(
    open val name: String,
    open val price: String,
    open val marketType: String,
    open val type: String,
    open var seller: String,
    open val unit: String,
    var quantityOrdered: Int = 0

)

fun Map<String, Any>.toProduct(): Product {
    return Product(
        this["name"].toString(),
        this["price"].toString(),
        this["marketType"].toString(),
        this["type"].toString(),
        this["seller"].toString(),
        this["unit"].toString(),
        this["quantityOrdered"].toString().toInt()

    )


}

fun Product.getPhotoUri(): LiveData<String> {
    return when (this.marketType) {
        "superMarket" ->
            ProductRepo.getSuperMarketProductPhoto(this.type)
        "vegetable" ->
            ProductRepo.getVegetableProductPhoto(this.type)
        "restaurant" ->
            ProductRepo.getRestaurantProductPhoto(this.seller, this.name)
        "individual" ->
            ProductRepo.getIndividualProductPhoto(this.type, this.name)
        else -> object : LiveData<String>() {
            override fun onActive() {
                super.onActive()
                value = null
            }
        }
    }
}






