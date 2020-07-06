package com.omaraboesmail.bargain.pojo

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.DocumentReference
import com.omaraboesmail.bargain.utils.checkItemsAre

data class SuperMarket(
    override val id: String,
    override val name: String,
    override val address: String,
    override val phone: String,
    override val discount: Float,
    var photo: String,
    override var products: List<DocumentReference> = ArrayList()
) : Store(id, name, address, phone, discount, products)

fun Map<String, Any>.toSuperMarket(): SuperMarket? {
    if (this["products"] != null) {
        val array = (this["products"] as List<*>).checkItemsAre<DocumentReference>()
        if (!array.isNullOrEmpty())
            return SuperMarket(
                this["id"].toString(),
                this["name"].toString(),
                this["address"].toString(),
                this["phone"].toString(),
                this["discount"].toString().toFloat(),
                this["photo"].toString(),
                array
            )
    }
    return SuperMarket(
        this["id"].toString(),
        this["name"].toString(),
        this["address"].toString(),
        this["phone"].toString(),
        this["discount"].toString().toFloat(),
        this["photo"].toString()

    )
}

fun SuperMarket.toHashMap(): HashMap<String, Any> {
    return hashMapOf(
        "id" to this.id,
        "name" to this.name,
        "phone" to this.phone,
        "address" to this.address,
        "discount" to this.discount,
        "photo" to this.photo,
        "products" to this.products
    )

}

fun SuperMarket.getProductsList(): LiveData<List<SupermarketProduct>> {
    return object : LiveData<List<SupermarketProduct>>() {
        override fun onActive() {
            super.onActive()
            val productList = ArrayList<SupermarketProduct>()
            val seller = this@getProductsList.name
            for (product in this@getProductsList.products) {
                (product).get().addOnSuccessListener {
                    if (it.data != null) {
                        val productData = it.data!!.toSuperMarketProduct()
                        productData.seller = seller
                        productList.add(productData)
                        value = productList
                    }
                }
            }
            productList.clear()
        }
    }
}