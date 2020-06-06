package com.omaraboesmail.bargain.resultStats

enum class UserVerState(var state: Boolean, var msg: String) {
    VERIFIED(true, "Your Account is Verified"),
    UNVERIFIED(false, "Verify your account first")
}