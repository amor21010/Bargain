package com.omaraboesmail.bargain.resultStats

enum class DbCRUDState(var msg: String) {
    INSERTED("Inserted successfully."),
    FAILED("Operation failed."),
    DELETED("DELETED successfully."),
    UPDATED("UPDATED successfully"),
    LOADING("Loading..."),
    EXCEPTION("something went wrong check your connection")
}