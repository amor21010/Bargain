package com.omaraboesmail.bargain.data.costumeOrderRepo

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.omaraboesmail.bargain.data.EntityMapper
import com.omaraboesmail.bargain.pojo.CostumeOrder
import com.omaraboesmail.bargain.pojo.DeliveryStat

class CostumeOrderMapper :
    EntityMapper<Map<String, Any>, CostumeOrder> {
    override fun mapFromEntity(entity: DocumentSnapshot): CostumeOrder {
        return CostumeOrder(
            email = entity["email"].toString(),
            time = (entity.get("time") as Timestamp),
            state = getState(entity.get("state").toString()),
            order = entity["order"].toString()
        )
    }

    override fun mapToEntity(domainModel: CostumeOrder): Map<String, Any> {
        return hashMapOf(
            "email" to domainModel.email,
            "time" to domainModel.time,
            "order" to domainModel.order,
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