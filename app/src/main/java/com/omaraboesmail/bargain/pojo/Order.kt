package com.omaraboesmail.bargain.pojo

import com.google.firebase.Timestamp

data class Order(
    var cart: Cart,
    val time: Timestamp = Timestamp.now(),
    val totalPrice: Double,
    var state: DeliveryStat
)

