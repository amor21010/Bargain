package com.omaraboesmail.bargain.pojo

data class User(
    var id: String,
    var name: String,
    var email: String,
    var phone: String,
    var password: String,
    var address: String,
    var photoUrl: String,
    var nationalId: String,
    var approved: Boolean,
    var trustPoints: Int
)