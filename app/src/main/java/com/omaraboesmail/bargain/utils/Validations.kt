package com.omaraboesmail.bargain.utils

import android.util.Patterns

fun CharSequence?.isValidInputAndNotShort() =
    !isNullOrEmpty() && (this!!.length > 6)

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this!!)
        .matches() && this.isValidInputAndNotShort()

fun CharSequence?.isValidPassword() =
    !isNullOrEmpty() && this!!.length > 8

fun CharSequence?.isValidInputAndNotVeryShort() =
    !isNullOrEmpty() && (this!!.length > 3)

fun CharSequence?.isValidInput() = !isNullOrEmpty()
