package com.omaraboesmail.bargain.resultStats

enum class AuthState(var msg: String) {
    SUCCESS("Authentication success."),
    FAILED("Authentication failed."),
    EXCEPTION("Authentication exception."),
    UNAUTHORIZED("Your Are Not Authorized"),
    LOADING("Loading..."),
    SIGNEDOUT("Signed out"),

}