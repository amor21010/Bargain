package com.omaraboesmail.bargain.pojo

data class CostumeOrder(
    val email: String,
    var order: String,
    var time: String,
    var state: DeliveryStat
)

