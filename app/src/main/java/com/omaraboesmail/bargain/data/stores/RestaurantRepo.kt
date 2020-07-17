package com.omaraboesmail.bargain.data.stores

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.omaraboesmail.bargain.data.FireBaseConst.restaurantDB
import com.omaraboesmail.bargain.pojo.Restaurant
import com.omaraboesmail.bargain.pojo.Store
import com.omaraboesmail.bargain.pojo.toRestaurant
import com.omaraboesmail.bargain.resultStats.DbCRUDState
import com.omaraboesmail.bargain.resultStats.UploadPhotoState
import com.omaraboesmail.bargain.utils.Const.TAG

object RestaurantRepo : StoreInterface {

    var restaurantDoc = MutableLiveData<DocumentReference>()
    val restaurantName = MutableLiveData<String>()
    var uploadProgress = MutableLiveData<Double>()
    val uploadStat = MutableLiveData<UploadPhotoState>().apply {
        value = UploadPhotoState.LOADING
    }
    val crudState = MutableLiveData<DbCRUDState>().apply {
        value = DbCRUDState.LOADING
    }
    val restaurantByName: LiveData<Store> = Transformations.switchMap(restaurantName) {
        getStoreByName(it)
    }

    fun setRestaurantName(name: String) {
        Log.d(TAG, name + restaurantName.value)
        if (restaurantName.value != name) restaurantName.value = name
    }

    override fun getStoreByName(name: String): LiveData<Store>? {
        return object : LiveData<Store>() {
            override fun onActive() {
                super.onActive()
                restaurantDB.whereEqualTo("name", name).get().addOnSuccessListener {
                    if (!it.isEmpty) {
                        restaurantDoc.value = it.documents[0].reference
                        value = it.documents[0]!!.data?.toRestaurant()
                    }
                }
            }
        }

    }


    override fun getAllStores(): LiveData<List<Store>> {
        return object : LiveData<List<Store>>() {
            override fun onActive() {
                super.onActive()
                restaurantDB.orderBy("name", Query.Direction.ASCENDING)
                    .addSnapshotListener { snapshot, e ->
                        if (e != null) return@addSnapshotListener
                        if (snapshot != null) {
                            val it = snapshot.documents
                            val list: MutableList<Restaurant> = ArrayList()
                            for (Restaurant in it)
                                Restaurant.data?.toRestaurant()?.let { it1 -> list.add(it1) }
                            value = list
                        }

                    }
            }
        }
    }


    /* fun uploadRestaurantImage(file: Uri, restaurant: Restaurant) {
         uploadStat.value = UploadPhotoState.LOADING
         mStorageRef.child("Restaurant /${restaurant.name}").putFile(file)
             .addOnSuccessListener {
                 uploadStat.value = UploadPhotoState.SUCCESS
                 mStorageRef.child("Restaurant /${restaurant.name}").downloadUrl.addOnCompleteListener {
                     if (it.isSuccessful) {


                         restaurant.photo = it.result.toString()
                         Log.d(TAG, restaurant.photo + " a")
                         updateRestaurant(restaurant)
                     }
                 }


             }.addOnProgressListener {
                 uploadStat.value = UploadPhotoState.LOADING
                 Log.d(TAG, (it.bytesTransferred * 100 / it.totalByteCount).toDouble().toString())
                 uploadProgress.value = (it.bytesTransferred * 100 / it.totalByteCount).toDouble()
             }
             .addOnFailureListener {
                 uploadStat.value = UploadPhotoState.FAILED
                 Log.d(TAG, it.message.toString())
             }
     }
 */
/*
    fun createRestaurant(image: Uri, restaurant: Restaurant) {
        crudState.value = DbCRUDState.LOADING
        restaurantDB.document(restaurant.name).get().addOnSuccessListener {
            Log.d(TAG, it.toString())
            if (it.exists()) { //the same name inserted before
                DbCRUDState.FAILED.msg = "this restaurant was inserted before"
                crudState.value = DbCRUDState.FAILED
            } else //not inserted before
                restaurantDB.document(restaurant.name).set(restaurant).addOnSuccessListener {
                    crudState.value = DbCRUDState.INSERTED
                    restaurantDoc.value = restaurantDB.document(restaurant.name)
                    try {
                        uploadRestaurantImage(image, restaurant)
                    } catch (e: Exception) {
                        uploadStat.value = UploadPhotoState.FAILED
                    }
                }.addOnFailureListener {
                    crudState.value = DbCRUDState.FAILED

                }
        }
    }

    fun updateRestaurant(newRestaurant: Restaurant) {
        restaurantDoc.value?.update(newRestaurant.toHashMap())?.addOnCompleteListener {
            if (it.isSuccessful) crudState.value = DbCRUDState.UPDATED
            else crudState.value = DbCRUDState.FAILED
        }
    }

*/


}


