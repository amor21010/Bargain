package com.omaraboesmail.bargain.data

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.omaraboesmail.bargain.pojo.SuperMarket
import com.omaraboesmail.bargain.pojo.toHashMap
import com.omaraboesmail.bargain.pojo.toSuperMarket
import com.omaraboesmail.bargain.resultStats.DbCRUDState
import com.omaraboesmail.bargain.resultStats.UploadPhotoState
import com.omaraboesmail.bargain.utils.Const.TAG

object SuperMarketRepo {
    private val fireStore = FirebaseFirestore.getInstance()
    val superMarketDB = fireStore.collection("super markets")
    var marketDoc = MutableLiveData<DocumentReference>()
    val marketName = MutableLiveData<String>()
    var uploadProgress = MutableLiveData<Double>()
    val uploadStat = MutableLiveData<UploadPhotoState>().apply {
        value = UploadPhotoState.LOADING
    }
    val crudState = MutableLiveData<DbCRUDState>().apply {
        value = DbCRUDState.LOADING
    }
    val SuperMarketByName: LiveData<SuperMarket> = Transformations.switchMap(marketName) {
        getMarketData(it)
    }

    fun setMarketName(name: String) {
        Log.d(TAG, name + marketName.value)
        if (marketName.value != name) marketName.value = name
    }

    private fun getMarketData(name: String): LiveData<SuperMarket>? {
        return object : LiveData<SuperMarket>() {
            override fun onActive() {
                super.onActive()
                superMarketDB.whereEqualTo("name", name).get().addOnSuccessListener {
                    if (!it.isEmpty) {
                        marketDoc.value = it.documents[0].reference
                        value = it.documents[0]!!.data?.toSuperMarket()
                    }
                }
            }
        }

    }

    fun getAllMarkets(): LiveData<List<SuperMarket>> {
        return object : LiveData<List<SuperMarket>>() {
            override fun onActive() {
                super.onActive()
                superMarketDB.orderBy("name", Query.Direction.ASCENDING).get()
                    .addOnSuccessListener {
                        if (!it.isEmpty) {
                            val lsit: MutableList<SuperMarket> = ArrayList()
                            for (market in it.documents)
                                market.data?.toSuperMarket()?.let { it1 -> lsit.add(it1) }
                            Log.d(TAG, lsit[0].toString())
                            value = lsit
                        }
                    }
            }
        }
    }

    fun uploadSuperMarketImage(file: Uri, superMarket: SuperMarket) {
        uploadStat.value = UploadPhotoState.LOADING
        fireStorage.mStorageRef.child("super market image /${superMarket.name}").putFile(file)
            .addOnSuccessListener {
                uploadStat.value = UploadPhotoState.SUCCESS
                fireStorage.mStorageRef.child("super market image /${superMarket.name}").downloadUrl.addOnCompleteListener {
                    if (it.isSuccessful) {

                        Log.d(TAG, superMarket.photo + " b")
                        superMarket.photo = it.result.toString()
                        Log.d(TAG, superMarket.photo + " a")
                        updateMarket(superMarket)
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

    fun createMarket(image: Uri, superMarket: SuperMarket) {
        crudState.value = DbCRUDState.LOADING
        superMarketDB.document(superMarket.name).get().addOnSuccessListener {
            Log.d(TAG, it.toString())
            if (it.exists()) { //the same name inserted before
                DbCRUDState.FAILED.msg = "this super market was inserted before"
                crudState.value = DbCRUDState.FAILED
            } else //not inserted before
                superMarketDB.document(superMarket.name).set(superMarket).addOnSuccessListener {
                    crudState.value = DbCRUDState.INSERTED
                    marketDoc.value = superMarketDB.document(superMarket.name)
                    try {
                        uploadSuperMarketImage(image, superMarket)
                    } catch (e: Exception) {
                        uploadStat.value = UploadPhotoState.FAILED
                    }
                }.addOnFailureListener {
                    crudState.value = DbCRUDState.FAILED

                }
        }
    }

    fun updateMarket(newMarket: SuperMarket) {
        marketDoc.value?.update(newMarket.toHashMap())?.addOnCompleteListener {
            if (it.isSuccessful) crudState.value = DbCRUDState.UPDATED
            else crudState.value = DbCRUDState.FAILED
        }
    }
}


