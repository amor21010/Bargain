package com.omaraboesmail.bargain.data.costumeOrderRepo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.Query
import com.omaraboesmail.bargain.data.FireBaseConst
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.pojo.CostumeOrder
import com.omaraboesmail.bargain.pojo.DeliveryStat
import com.omaraboesmail.bargain.resultStats.DbCRUDState
import com.omaraboesmail.bargain.utils.Const.TAG

object CostumeOrderRepo : CostumeOrderInterface {
    val orderMapper = CostumeOrderMapper()
    private val _orderPlaceState = MutableLiveData<DbCRUDState>()
    val orderStat: LiveData<DbCRUDState> = _orderPlaceState
    override fun getAllUserOrder(email: String): LiveData<List<CostumeOrder>> {
        return object : LiveData<List<CostumeOrder>>() {
            override fun onActive() {
                super.onActive()
                FireBaseConst.costumeOrderDB.whereEqualTo("email", email)
                    .orderBy("time", Query.Direction.DESCENDING)
                    .addSnapshotListener { snap, exception ->
                        if (exception != null) {
                            Log.d(TAG, "onActive: $exception")
                            return@addSnapshotListener
                        }
                        if (snap != null) {
                            val data = snap.documents.map { orderMapper.mapFromEntity(it) }
                            value = data
                        }
                    }
            }

        }


    }

    override fun createOrder(order: String) {
        _orderPlaceState.value = DbCRUDState.LOADING
        UserRepo.fbUserLive.value?.email?.let { email ->
            val costumeOrder = CostumeOrder(
                email = email,
                order = order,
                state = DeliveryStat.WAITING
            )
            FireBaseConst.costumeOrderDB.add(orderMapper.mapToEntity(costumeOrder))
                .addOnSuccessListener {
                    Log.d(TAG, "createOrder: $it")
                    _orderPlaceState.value = DbCRUDState.INSERTED
                }
                .addOnFailureListener {
                    _orderPlaceState.value = DbCRUDState.FAILED
                }

        }

    }

}