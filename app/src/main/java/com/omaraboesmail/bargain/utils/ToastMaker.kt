package com.omaraboesmail.bargain.utils

import android.content.Context
import android.widget.Toast

class ToastMaker(context: Context, msg: String) {
    init {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}