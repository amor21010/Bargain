package com.omaraboesmail.bargain.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.resultStats.AuthState
import com.omaraboesmail.bargain.singiltons.FireBaseAuthenticate.firebaseAuthInstance


object DialogMaker {

    lateinit var mContext: AppCompatActivity
    var mTitle = MutableLiveData<String>()
    var loading = MutableLiveData<Boolean>()
    var loadingstate = MutableLiveData<String>()
    var progress = MutableLiveData(0.0)
    var email: String = " "

    fun authDialog(): Dialog {
        mTitle.value = AuthState.LOADING.msg
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.auth_dialoug)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val body = dialog.findViewById(R.id.loadingState) as TextView
        mTitle.observe(
            mContext, Observer {
                body.text = it
            })

        val progressBar = dialog.findViewById(R.id.loading) as ProgressBar
        val image = dialog.findViewById(R.id.unAuthorized) as ImageView

        loading.observe(
            mContext, Observer {
                if (it) {
                    progressBar.visibility = View.VISIBLE
                    image.visibility = View.GONE
                    dialog.setCancelable(false)
                } else {
                    progressBar.visibility = View.GONE
                    image.visibility = View.VISIBLE
                    dialog.setCancelable(true)
                }
            })

        return dialog
    }

    @SuppressLint("SetTextI18n")
    fun verifyEmailDialog(isDismissable: Boolean = true): Dialog {
        mTitle.value = AuthState.LOADING.msg
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.verify_email_dialoug)
        dialog.setCancelable(isDismissable)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val body = dialog.findViewById(R.id.text) as TextView
        val verify = dialog.findViewById(R.id.verify) as TextView
        val goToMail = dialog.findViewById(R.id.gotMail) as TextView

        body.text = mContext.getString(R.string.email_not_verified) + "\n" + email
        verify.text = mContext.getString(R.string.try_to_verify)
        goToMail.text = mContext.getString(R.string.got_to_email_app)

        val image = dialog.findViewById(R.id.notVerified) as ImageView
        verify.setOnClickListener {
            if (!firebaseAuthInstance.currentUser!!.isEmailVerified)
                firebaseAuthInstance.currentUser!!.sendEmailVerification()
            else authDialog().dismiss()
        }
        goToMail.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_APP_EMAIL)
            mContext.startActivity(
                Intent.createChooser(
                    intent,
                    mContext.getString(R.string.got_to_email_app)
                )
            )
        }




        return dialog
    }

    fun Dialog.setEmail(email: String) {
        DialogMaker.email = email
    }

    @SuppressLint("SetTextI18n")
    fun uploadPhotoProgressDialog(): Dialog {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.upload_dialoug)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val progressBar = dialog.findViewById(R.id.progress) as ProgressBar
        val loading = dialog.findViewById(R.id.loading) as TextView
        val progressText = dialog.findViewById(R.id.progressText) as TextView
        val okButton = dialog.findViewById(R.id.button) as Button
        dialog.setCancelable(false)

        progress.observe(mContext, Observer {
            progressBar.progress = it.toInt()
            if (it == 100.0) {
                progressBar.progressDrawable.setColorFilter(
                    Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN
                )
                dialog.setCancelable(true)
                okButton.isEnabled = true
                okButton.setOnClickListener {
                    if (dialog.isShowing) dialog.dismiss()
                    NavigationFlow(mContext).navigateToFragment(R.id.nav_super_market)
                }

            } else if (it <= 30) progressBar.progressDrawable.setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN
            )
            else if (it < 70) progressBar.progressDrawable.setColorFilter(
                Color.parseColor("#F7A63E"), android.graphics.PorterDuff.Mode.SRC_IN
            )
            else if (it >= 70) progressBar.progressDrawable.setColorFilter(
                Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN
            )
            else {
                dialog.setCancelable(false)
                okButton.isEnabled = false
                okButton.setOnClickListener(null)
            }


            progressText.text = it.toString().split(".")[0] + "%"

        })
        loadingstate.observe(mContext, Observer {
            loading.text = it
        })


        return dialog
    }


}