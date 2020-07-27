package com.omaraboesmail.bargain.pojo

import com.google.firebase.Timestamp

data class CostumeOrder(
    val email: String,
    var order: String,
    var time: Timestamp = Timestamp.now(),
    var state: DeliveryStat
)

