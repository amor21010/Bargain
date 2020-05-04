package com.omaraboesmail.bargain


import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        haveAccount.setOnClickListener {
            intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        signUpBtn.setOnClickListener {
            validateAll()
        }

    }

    fun validateAll(): Boolean {
        if ((!validateEmail())
            or (!validatePassword())
            or !name.validateInput()
            or !address.validateInput()
            or !phone.validateInput()
            or !conPassword()
        ) {

            Toast.makeText(this, "please enter the missing fields", Toast.LENGTH_LONG)
                .show()
            return false

        } else {
            register()

            return true
        }

    }

    fun register() {
        //todo register the user
        Toast.makeText(this, "registered user", Toast.LENGTH_LONG).show()

    }

    fun validatePassword(): Boolean {
        if (password.editText!!.text.isValidPassword()) {
            password.error = null
            return true
        } else {
            password.error = "please enter valid Password"
            return false
        }
    }

    fun conPassword(): Boolean {
        if (password.editText!!.text.isValidPassword()) {
            if (password.editText!!.text.equals(confPassword.editText!!.text))
                confPassword.error = null
            return true
        } else {
            confPassword.error = "password dose not match"
            return false
        }
    }

    fun TextInputLayout.validateInput(): Boolean {
        val editText = this.editText
        if (editText!!.text.isValidInput()) {
            this.error = null
            return true
        } else {
            this.error = "please enter valid Password"
            return false
        }
    }

    fun validateEmail(): Boolean {
        if (email.editText!!.text.isValidEmail()) {
            email.error = null
            return true
        } else {
            email.error = "please enter valid Password"
            return false
        }
    }

}

fun CharSequence?.isValidInput() =
    !isNullOrEmpty() and (this?.length!! < 6)

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() and Patterns.EMAIL_ADDRESS.matcher(this!!).matches() and this.isValidInput()

fun CharSequence?.isValidPassword() =
    !isNullOrEmpty() && this!!.length < 8

