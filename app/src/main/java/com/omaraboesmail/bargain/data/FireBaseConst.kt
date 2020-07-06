package com.omaraboesmail.bargain.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.omaraboesmail.bargain.resultStats.AuthState
import com.omaraboesmail.bargain.resultStats.DbCRUDState

object FireBaseConst {
    val mStorageRef = FirebaseStorage.getInstance().reference
    private val fireStore = FirebaseFirestore.getInstance()

    val superMarketProductsDB = fireStore.collection("supermarket products")
    val superMarketDB = fireStore.collection("super markets")
    val restaurantDB = fireStore.collection("restaurants")
    val vegetableDB = fireStore.collection("/vegetable/")
    fun individualsDB(type: String) = fireStore.collection("/$type/")
    val firebaseAuthInstance = FirebaseAuth.getInstance()
    val authState = MutableLiveData<AuthState>().apply { value = AuthState.LOADING }

    val usersColl = fireStore.collection("users")
    val cartsDB = fireStore.collection("userCart")
    val dbCRUDState = MutableLiveData<DbCRUDState>().apply { value = DbCRUDState.LOADING }

}