package com.omaraboesmail.bargain

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        signIn.setOnClickListener {
            intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        signUp.setOnClickListener {
            intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}
