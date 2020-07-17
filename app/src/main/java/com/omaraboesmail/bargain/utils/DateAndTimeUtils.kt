package com.omaraboesmail.bargain.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateAndTimeUtils {
    @SuppressLint("SimpleDateFormat")
    fun getDateAndTime(): String {
        val formatter = SimpleDateFormat("dd/MM 'at' hh:mm:ss a")
        val currentTime = Date()
        return formatter.format(currentTime)
    }
}