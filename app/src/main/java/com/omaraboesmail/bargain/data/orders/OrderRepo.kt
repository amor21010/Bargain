package com.omaraboesmail.bargain.data.orders

import android.util.Log
import androidx.lifecycle.LiveData
import com.omaraboesmail.bargain.data.FireBaseConst
import com.omaraboesmail.bargain.pojo.Order
import com.omaraboesmail.bargain.utils.Const.TAG

object OrderRepo : OrderInterface {
    val orderMapper = OrderMapper()

    override fun getAllUserOrder(email: String): LiveData<List<Order>> {
        return object : LiveData<List<Order>>() {
            override fun onActive() {
                super.onActive()
                FireBaseConst.orderDB.whereEqualTo("cart.email", email)
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
        FireBaseConst.orderDB.add(orderMapper.mapToEntity(order)).addOnSuccessListener {
            Log.d(TAG, "createOrder: $it")
        }
    }

}