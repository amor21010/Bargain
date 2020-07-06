package com.omaraboesmail.bargain.utils

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.getText(errorMsg: String = "this field must be filled"): String? {
    return if (this.editText?.text?.isNotEmpty()!!) {
        this.error = null
        this.editText?.text?.toString()?.trim()
    } else {
        this.error = errorMsg
        null
    }
}

fun TextInputLayout.validateInput(errorMsg: String = "please enter valid data"): Boolean {
    val editText = this.editText
    return if (editText != null)
        return if (editText.text.isValidInput()) {
            this.error = null
            true
        } else {
            this.error = errorMsg
            false
        }
    else false
}

