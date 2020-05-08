package com.omaraboesmail.bargain.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.omaraboesmail.bargain.NavigationFlow
import com.omaraboesmail.bargain.R

import com.omaraboesmail.bargain.singiltons.FireBaseAuthenticate.fbUser

import com.omaraboesmail.bargain.ui.mainActivity.MainActivity
import com.omaraboesmail.bargain.ui.signInActivity.SignInActivity
import com.omaraboesmail.bargain.ui.signUpActivity.SignUpActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (fbUser != null) {
            NavigationFlow(this).navigateActivity(MainActivity())
        } else {
            signIn.setOnClickListener {
                NavigationFlow(this).navigateActivity(SignInActivity(), true)

            }
            signUp.setOnClickListener {
                NavigationFlow(this).navigateActivity(SignUpActivity(), true)

            }
        }
    }
}