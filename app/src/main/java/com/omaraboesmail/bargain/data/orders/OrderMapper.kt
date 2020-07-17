package com.omaraboesmail.bargain.data.orders

import com.google.firebase.firestore.DocumentSnapshot
import com.omaraboesmail.bargain.data.EntityMapper
import com.omaraboesmail.bargain.pojo.DeliveryStat

import com.omaraboesmail.bargain.pojo.Order
import com.omaraboesmail.bargain.pojo.toCartObject

class OrderMapper :
    EntityMapper<Map<String, Any>, Order> {
    override fun mapFromEntity(entity: DocumentSnapshot): Order {
        return Order(
            (entity["cart"] as HashMap<String, Any>).toCartObject(),
            entity.get("time").toString(),
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
            else -> DeliveryStat.WAITING

        }
    }

}