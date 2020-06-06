package com.omaraboesmail.bargain.utils

import com.google.android.material.textfield.TextInputLayout

class TextInputLayoutUtil {
    fun getText(textInputLayout: TextInputLayout): String? {
        return if (textInputLayout.editText?.text?.isNotEmpty()!!) {
            textInputLayout.error = null
            textInputLayout.editText?.text?.toString()?.trim()
        } else {
            textInputLayout.error = "this field must be filled"
            null
        }
    }
}