package com.omaraboesmail.bargain.pojo

import com.omaraboesmail.bargain.utils.DateAndTimeUtils

data class Order(
    var cart: Cart,
    val time: String = DateAndTimeUtils().getDateAndTime(),
    val totalPrice: Double,
    var state: DeliveryStat
)