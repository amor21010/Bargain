package com.omaraboesmail.bargain.pojo

enum class DbCRUDState(var msg: String) {
    INSERTED("Inserted successfully."),
    FAILED("Operation failed."),
    DELETED("DELETED successfully."),
    UPDATED("UPDATED successfully"),
    LOADING("Loading...")
}