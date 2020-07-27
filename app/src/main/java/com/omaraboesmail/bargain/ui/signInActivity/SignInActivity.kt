package com.omaraboesmail.bargain.ui.signInActivity

import android.app.Dialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.FireBaseConst.firebaseAuthInstance
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.resultStats.AuthState
import com.omaraboesmail.bargain.ui.SplashActivity
import com.omaraboesmail.bargain.ui.mainActivity.MainActivity
import com.omaraboesmail.bargain.utils.DialogMaker
import com.omaraboesmail.bargain.utils.DialogMaker.loading

import com.omaraboesmail.bargain.utils.DialogMaker.mTitle
import com.omaraboesmail.bargain.utils.NavigationFlow
import com.omaraboesmail.bargain.utils.getText
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    private val signInViewModel: SignInViewModel by viewModels()
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        DialogMaker.mContext = this
        dialog = DialogMaker.authDialog()
        signInBtn.setOnClickListener {
            if ((email.getText() != null) and (password.getText() != null)) {
                signInViewModel.signIn(email.getText()!!, password.getText()!!)
                signInViewModel.isAuthorized().observe(this, Observer {
                    when (it) {
                        AuthState.LOADING -> {
                            showDialog(it.msg, true)
                        }
                        AuthState.UNAUTHORIZED -> {
                            showDialog(it.msg, false)
                        }
                        AuthState.SUCCESS -> {
                            dialog.dismiss()
                            UserRepo.setFirebaseUser(firebaseAuthInstance.currentUser)
                            NavigationFlow(this).navigateActivity(MainActivity())
                        }
                        else -> {
                            showDialog(it.msg, false)
                        }

                    }

                })

            }
        }

    }


    private fun showDialog(msg: String, boolean: Boolean) {
        loading.value = boolean
        mTitle.value = msg

        if (dialog.isShowing) {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onBackPressed() {
        NavigationFlow(this).navigateActivity(SplashActivity())
    }

}
