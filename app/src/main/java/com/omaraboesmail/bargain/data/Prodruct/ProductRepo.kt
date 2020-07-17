package com.omaraboesmail.bargain.data.Prodruct


import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.omaraboesmail.bargain.data.FireBaseConst
import com.omaraboesmail.bargain.data.stores.SuperMarketRepo
import com.omaraboesmail.bargain.pojo.IndividualProduct
import com.omaraboesmail.bargain.pojo.RestaurantProduct
import com.omaraboesmail.bargain.pojo.SupermarketProduct
import com.omaraboesmail.bargain.pojo.VegetableProduct
import com.omaraboesmail.bargain.resultStats.UploadPhotoState
import com.omaraboesmail.bargain.utils.Const


object ProductRepo {
    val uploadProgress = MutableLiveData<Double>()

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

    fun getIndividualProductPhoto(type: String, name: String, seller: String): LiveData<String> {
        return object : LiveData<String>() {
            override fun onActive() {
                super.onActive()
                FireBaseConst.mStorageRef.child("product/individuals/${type}/$seller/${name}.jpg").downloadUrl.addOnSuccessListener {
                    value = it.toString()
                }
            }
        }
    }

    fun uploadIndividualProductImage(file: Uri, individualProduct: IndividualProduct) {
        SuperMarketRepo.uploadStat.value = UploadPhotoState.LOADING
        FireBaseConst.mStorageRef
            .child("product/individuals/${individualProduct.type}/${individualProduct.seller}/${individualProduct.name}.jpg")
            .putFile(file)
            .addOnProgressListener {
                Log.d(
                    Const.TAG,
                    (it.bytesTransferred * 100 / it.totalByteCount).toDouble().toString()
                )
                uploadProgress.value =
                    (it.bytesTransferred * 100 / it.totalByteCount).toDouble()
            }
    }


    fun RestaurantProduct.getPhotoUri(restaurantName: String) =
        getRestaurantProductPhoto(
            restaurantName,
            this.name
        )

    fun SupermarketProduct.getPhotoUri() =
        getSuperMarketProductPhoto(
            this.type
        )

    fun VegetableProduct.getPhoto() =
        getVegetableProductPhoto(
            this.type
        )

    fun IndividualProduct.getPhoto() =
        getIndividualProductPhoto(
            this.type,
            this.name,
            this.seller
        )
}