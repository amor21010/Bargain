package com.omaraboesmail.bargain.data.stores

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.omaraboesmail.bargain.data.FireBaseConst.mStorageRef
import com.omaraboesmail.bargain.data.FireBaseConst.superMarketDB
import com.omaraboesmail.bargain.pojo.SuperMarket
import com.omaraboesmail.bargain.pojo.toHashMap
import com.omaraboesmail.bargain.pojo.toSuperMarket
import com.omaraboesmail.bargain.resultStats.DbCRUDState
import com.omaraboesmail.bargain.resultStats.UploadPhotoState
import com.omaraboesmail.bargain.utils.Const.TAG

object SuperMarketRepo {

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

    fun getMarketsWithDiscount(): LiveData<List<SuperMarket>> {
        return object : LiveData<List<SuperMarket>>() {
            override fun onActive() {
                super.onActive()
                superMarketDB.whereGreaterThan("discount", 0)
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null) return@addSnapshotListener
                        if (snapshot != null) {
                            value = snapshot.documents.map {
                                it.data?.toSuperMarket()!!
                            }
                        }
                    }


            }
        }
    }


    fun getAllMarkets(): LiveData<List<SuperMarket>> {
        return object : LiveData<List<SuperMarket>>() {
            override fun onActive() {
                super.onActive()
                superMarketDB.orderBy("name", Query.Direction.ASCENDING)
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null) return@addSnapshotListener
                        if (snapshot != null) {
                            val it = snapshot.documents
                            if (!it.isNullOrEmpty()) {
                                val list = ArrayList<SuperMarket>()
                                for (market in it)
                                    market.data?.toSuperMarket()?.let { it1 -> list.add(it1) }
                                value = list
                            }
                        }
                    }
            }
        }
    }

    private fun uploadSuperMarketImage(file: Uri, superMarket: SuperMarket) {
        uploadStat.value = UploadPhotoState.LOADING
        mStorageRef.child("super market image /${superMarket.name}").putFile(file)
            .addOnSuccessListener {
                uploadStat.value = UploadPhotoState.SUCCESS
                mStorageRef.child("super market image /${superMarket.name}").downloadUrl.addOnCompleteListener {
                    if (it.isSuccessful) {

                        Log.d(TAG, superMarket.photo + " b")
                        superMarket.photo = it.result.toString()
                        Log.d(TAG, superMarket.photo + " a")
                        updateMarket(
                            superMarket
                        )
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
                        uploadSuperMarketImage(
                            image,
                            superMarket
                        )
                    } catch (e: Exception) {
                        uploadStat.value = UploadPhotoState.FAILED
                    }
                }.addOnFailureListener {
                    crudState.value = DbCRUDState.FAILED

                }
        }
    }

    private fun updateMarket(newMarket: SuperMarket) {
        marketDoc.value?.update(newMarket.toHashMap())?.addOnCompleteListener {
            if (it.isSuccessful) crudState.value = DbCRUDState.UPDATED
            else crudState.value = DbCRUDState.FAILED
        }
    }

}


