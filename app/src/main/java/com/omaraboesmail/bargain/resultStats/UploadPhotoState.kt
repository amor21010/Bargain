package com.omaraboesmail.bargain.resultStats

enum class UploadPhotoState(var msg: String) {
    SUCCESS("uploaded successfully."),
    FAILED("photo upload failed."),
    EXCEPTION("photo upload throw exception."),
    LOADING("Loading..."),
}