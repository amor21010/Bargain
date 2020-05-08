package com.omaraboesmail.bargain.pojo

enum class UserVerState(var state: Boolean, var msg: String) {
    VERIFIED(true, "Your Account is Verified"),
    UNVERIFIED(false, "Verify your account first")
}