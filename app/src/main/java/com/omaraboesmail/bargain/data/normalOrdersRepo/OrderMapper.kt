package com.omaraboesmail.bargain.data.normalOrdersRepo

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.omaraboesmail.bargain.data.EntityMapper
import com.omaraboesmail.bargain.pojo.DeliveryStat
import com.omaraboesmail.bargain.pojo.Order
import com.omaraboesmail.bargain.pojo.toCartObject
import com.omaraboesmail.bargain.utils.Const.TAG
import java.util.*

@Suppress("UNCHECKED_CAST")
class OrderMapper :
    EntityMapper<Map<String, Any>, Order> {
    override fun mapFromEntity(entity: DocumentSnapshot): Order {
        Log.d(TAG, "mapFromEntity: ${entity.get("time")}")
        return Order(
            (entity["cart"] as HashMap<String, Any>).toCartObject(),
            (entity.get("time") as Timestamp),
            entity.getDouble("totalPrice")!!,
            getState(entity.get("state").toString())
        )
    }

    override fun mapToEntity(domainModel: Order): Map<String, Any> {
        return hashMapOf(
            "cart" to domainModel.cart,
            "time" to domainModel.time,
            "totalPrice" to domainModel.totalPrice,
            "state" to domainModel.state
        )
    }

    private fun getState(string: String): DeliveryStat {
        return when (string) {
            DeliveryStat.SHIPPED.name, DeliveryStat.SHIPPED.msg -> DeliveryStat.SHIPPED
            DeliveryStat.DELIVERED.name, DeliveryStat.DELIVERED.msg -> DeliveryStat.DELIVERED
            DeliveryStat.CANCELED.name, DeliveryStat.CANCELED.msg -> DeliveryStat.CANCELED
            else -> DeliveryStat.WAITING

        }
    }

}