package com.omaraboesmail.bargain

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

class DialogMaker {


    fun showDialog(context: Context, msg: String, isLoading: Boolean): Dialog {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.loading_dialoug)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val body = dialog.findViewById(R.id.loadingState) as TextView
        body.text = msg
        val progressBar = dialog.findViewById(R.id.loading) as ProgressBar
        val image = dialog.findViewById(R.id.unAuthorized) as ImageView


        if (isLoading) {
            progressBar.visibility = View.VISIBLE
            image.visibility = View.GONE
            dialog.setCancelable(false)
        } else {
            progressBar.visibility = View.GONE
            image.visibility = View.VISIBLE
            dialog.setCancelable(true)
        }

        return dialog
    }


}