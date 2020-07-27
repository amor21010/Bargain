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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.FireBaseConst.firebaseAuthInstance
import com.omaraboesmail.bargain.resultStats.AuthState
import com.omaraboesmail.bargain.resultStats.DbCRUDState
import com.omaraboesmail.bargain.ui.mainActivity.MainActivity


object DialogMaker {
    var mContext: AppCompatActivity = MainActivity()
    var mTitle = MutableLiveData<String>()
    var loading = MutableLiveData<Boolean>()
    var loadingstate = MutableLiveData<String>()
    var progress = MutableLiveData(0.0)
    var email: String = " "
    private lateinit var navigationFlow: NavigationFlow
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

    fun orderDialog(dbCRUDState: LiveData<DbCRUDState>): Dialog {
        navigationFlow = NavigationFlow(mContext)
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.db_loading_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        val body = dialog.findViewById(R.id.loadingState) as TextView
        val dismiss = dialog.findViewById(R.id.dismiss) as TextView
        val progressBar = dialog.findViewById(R.id.loading) as ProgressBar
        val goHome: Button = dialog.findViewById(R.id.goHome)

        dbCRUDState.observe(mContext, Observer {
            when (it) {
                DbCRUDState.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    dismiss.visibility = View.GONE
                    goHome.visibility = View.GONE
                    dialog.setCancelable(false)
                    mTitle.value = "${it.msg}"
                }
                DbCRUDState.FAILED -> {
                    progressBar.visibility = View.GONE
                    dismiss.visibility = View.GONE
                    goHome.visibility = View.GONE
                    dialog.setCancelable(true)
                    mTitle.value = "${it.msg}\n please contact us on WhatsApp"
                }
                DbCRUDState.INSERTED -> {
                    progressBar.visibility = View.GONE
                    dismiss.visibility = View.VISIBLE
                    dialog.setCancelable(true)
                    goHome.visibility = View.VISIBLE
                    goHome.setOnClickListener {
                        dialog.dismiss()
                        navigationFlow.navigateToFragment(R.id.nav_home)
                    }
                    dismiss.setOnClickListener {
                        dialog.dismiss()
                    }
                    mTitle.value = "Order has been successfully placed"

                }
                else -> TODO()
            }
        })
        mTitle.observe(
            mContext, Observer {
                body.text = it
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

    @Suppress("DEPRECATION")
    @SuppressLint("SetTextI18n")
    fun uploadPhotoProgressDialog(): Dialog {
        progress.value = 0.0
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
            when {
                it == 100.0 -> {
                    progressBar.progressDrawable.setColorFilter(
                        Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN
                    )
                    dialog.setCancelable(true)
                    okButton.isEnabled = true
                    okButton.setOnClickListener {
                        if (dialog.isShowing) dialog.dismiss()
                        mContext.onBackPressed()
                    }

                }
                it <= 30 -> progressBar.progressDrawable.setColorFilter(
                    Color.RED, android.graphics.PorterDuff.Mode.SRC_IN
                )
                it < 70 -> progressBar.progressDrawable.setColorFilter(
                    Color.parseColor("#F7A63E"), android.graphics.PorterDuff.Mode.SRC_IN
                )
                it >= 70 -> progressBar.progressDrawable.setColorFilter(
                    Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN
                )
                else -> {
                    dialog.setCancelable(false)
                    okButton.isEnabled = false
                    okButton.setOnClickListener(null)
                }
            }


            progressText.text = it.toString().split(".")[0] + "%"

        })
        loadingstate.observe(mContext, Observer {
            loading.text = it
        })
        return dialog
    }


}


fun setEmail(email: String) {
    DialogMaker.email = email
}