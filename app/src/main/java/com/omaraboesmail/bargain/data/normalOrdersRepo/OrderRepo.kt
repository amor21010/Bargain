package com.omaraboesmail.bargain.data.normalOrdersRepo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.Query
import com.omaraboesmail.bargain.data.FireBaseConst
import com.omaraboesmail.bargain.pojo.Order
import com.omaraboesmail.bargain.resultStats.DbCRUDState
import com.omaraboesmail.bargain.utils.Const.TAG

object OrderRepo : OrderInterface {
    val orderMapper = OrderMapper()
    val dbCRUDState = MutableLiveData<DbCRUDState>()
    override fun getAllUserOrder(email: String): LiveData<List<Order>> {
        return object : LiveData<List<Order>>() {
            override fun onActive() {
                super.onActive()
                FireBaseConst.orderDB.whereEqualTo("cart.email", email)
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

    override fun createOrder(order: Order) {
        dbCRUDState.value = DbCRUDState.LOADING
        Log.d(TAG, "createOrder: ${order.time.toDate()}")
        FireBaseConst.orderDB.add(orderMapper.mapToEntity(order)).addOnSuccessListener {
            dbCRUDState.value = DbCRUDState.INSERTED
        }.addOnFailureListener {
            dbCRUDState.value = DbCRUDState.FAILED

        }
    }

}