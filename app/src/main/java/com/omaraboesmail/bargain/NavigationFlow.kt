package com.omaraboesmail.bargain

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity


class NavigationFlow(context: Context) {
    val mContext: Context = context

    fun navigateActivity(dest: AppCompatActivity, backable: Boolean = false) {
        val i = Intent(mContext, dest::class.java)
        if (!backable)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        mContext.startActivity(i)
    }
}