package com.omaraboesmail.bargain.ui.signInActivity

import android.app.Dialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputLayout
import com.omaraboesmail.bargain.DialogMaker
import com.omaraboesmail.bargain.NavigationFlow
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.ToastMaker
import com.omaraboesmail.bargain.pojo.AuthState
import com.omaraboesmail.bargain.ui.SplashActivity
import com.omaraboesmail.bargain.ui.mainActivity.MainActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    val signInViewModel: SignInViewModel by viewModels()
    lateinit var dialog: Dialog
    val dialogMaker = DialogMaker()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signInBtn.setOnClickListener {
            if (email.getText() != null
                && email.getText() != ""
                && password.getText() != null
                && password.getText() != ""
            ) {
                signInViewModel.signIn(email.getText()!!, password.getText()!!)
                signInViewModel.isAuthorized().observe(this, Observer {
                    when (it) {
                        AuthState.LOADING -> {
                            dialog = dialogMaker.showDialog(this, it.msg, true)
                            dialog.show(true)
                        }
                        AuthState.UNAUTHORIZED -> {
                            dialog.show(false)
                            dialog = dialogMaker.showDialog(this, it.msg, true)

                            dialog.show(true)
                        }
                        AuthState.SUCCESS ->
                            NavigationFlow(this).navigateActivity(MainActivity())
                        else -> {
                            dialog.show(false)
                            dialog = dialogMaker.showDialog(this, it.msg, true)
                            dialog.show(true)
                        }

                    }

                })

            } else {
                dialog = dialogMaker.showDialog(this, "you are missing some fields", false)
            }
        }

    }

    fun TextInputLayout.getText(): String? {
        if (this.editText?.text != null && this.editText != null) return this.editText!!.text.toString()
        else {
            ToastMaker(this@SignInActivity, "please Enter The Missing Fields")
            return null
        }
    }

    fun Dialog.show(boolean: Boolean) {
        if (this.isShowing) this.dismiss()
        if (boolean) this.show()
        else this.dismiss()
    }

    override fun onBackPressed() {
        NavigationFlow(this).navigateActivity(SplashActivity())
    }

}
