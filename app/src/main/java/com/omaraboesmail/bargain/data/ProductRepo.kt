package com.omaraboesmail.bargain.data


import androidx.lifecycle.LiveData
import com.omaraboesmail.bargain.pojo.IndividualProduct
import com.omaraboesmail.bargain.pojo.RestaurantProduct
import com.omaraboesmail.bargain.pojo.SupermarketProduct
import com.omaraboesmail.bargain.pojo.VegetableProduct


object ProductRepo {

    fun getSuperMarketProductPhoto(type: String): LiveData<String> {
        return object : LiveData<String>() {
            override fun onActive() {
                super.onActive()
                FireBaseConst.mStorageRef.child("product/superMarket/$type.jpg").downloadUrl.addOnSuccessListener {
                    value = it.toString()
                }
            }
        }
    }

    fun getVegetableProductPhoto(type: String): LiveData<String> {
        return object : LiveData<String>() {
            override fun onActive() {
                super.onActive()
                FireBaseConst.mStorageRef.child("product/vegetable/${type}.jpg").downloadUrl.addOnSuccessListener {
                    value = it.toString()
                }
            }
        }
    }

    fun getRestaurantProductPhoto(restaurantName: String, productName: String): LiveData<String> {
        return object : LiveData<String>() {
            override fun onActive() {
                super.onActive()

                FireBaseConst.mStorageRef.child("product/restaurant/$restaurantName/${productName}.jpg").downloadUrl.addOnSuccessListener {
                    value = it.toString()
                }
            }
        }
    }

    fun getIndividualProductPhoto(type: String, name: String): LiveData<String> {
        return object : LiveData<String>() {
            override fun onActive() {
                super.onActive()
                FireBaseConst.mStorageRef.child("product/individuals/${type}/${name}.jpg").downloadUrl.addOnSuccessListener {
                    value = it.toString()
                }
            }
        }
    }


    fun RestaurantProduct.getPhotoUri(restaurantName: String) =
        getRestaurantProductPhoto(restaurantName, this.name)

    fun SupermarketProduct.getPhotoUri() = getSuperMarketProductPhoto(this.type)
    fun VegetableProduct.getPhoto() = getVegetableProductPhoto(this.type)
    fun IndividualProduct.getPhoto() = getIndividualProductPhoto(this.type, this.name)
}