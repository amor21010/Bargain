package com.omaraboesmail.bargain.pojo

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.DocumentReference
import com.omaraboesmail.bargain.data.FireBaseConst
import com.omaraboesmail.bargain.utils.Const.TAG
import com.omaraboesmail.bargain.utils.checkItemsAre

data class Restaurant(
    override val id: String,
    override val name: String,
    override val address: String,
    override val phone: String,
    override val discount: Float,
    override var products: List<DocumentReference> = ArrayList()
) : Store(id, name, address, phone, discount, products)

fun Map<String, Any>.toRestaurant(): Restaurant? {
    val array = (this["products"] as List<*>).checkItemsAre<DocumentReference>()
    Log.d(TAG, array.toString() + listOf(this["products"]))
    if (!array.isNullOrEmpty())
        return Restaurant(
            this["id"].toString(),
            this["name"].toString(),
            this["address"].toString(),
            this["phone"].toString(),
            this["discount"].toString().toFloat(),
            array as ArrayList<DocumentReference>
        )
    else return Restaurant(
        this["id"].toString(),
        this["name"].toString(),
        this["address"].toString(),
        this["phone"].toString(),
        this["discount"].toString().toFloat()

    )
}


fun Restaurant.toHashMap(): HashMap<String, Any> {
    return hashMapOf(
        "id" to this.id,
        "name" to this.name,
        "phone" to this.phone,
        "address" to this.address,
        "discount" to this.discount,
        "products" to this.products
    )

}

fun Restaurant.getProductsList(): LiveData<List<RestaurantProduct>> {
    return object : LiveData<List<RestaurantProduct>>() {
        override fun onActive() {
            super.onActive()
            val productList = ArrayList<RestaurantProduct>()

            val seller = this@getProductsList.name
            for (product in this@getProductsList.products) {
                (product).get().addOnSuccessListener {
                    if (it.data != null) {
                        val productData = it.data!!.toRestaurantProduct()
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

fun Restaurant.getPhoto(): LiveData<String> {
    return object : LiveData<String>() {
        override fun onActive() {
            super.onActive()
            FireBaseConst.mStorageRef.child("restaurants/${this@getPhoto.name}.jpg").downloadUrl.addOnSuccessListener {
                value = it.toString()
            }
        }
    }
}