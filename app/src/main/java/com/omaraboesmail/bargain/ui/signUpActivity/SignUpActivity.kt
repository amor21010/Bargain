package com.omaraboesmail.bargain.ui.signUpActivity


import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputLayout
import com.omaraboesmail.bargain.NavigationFlow
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.pojo.AuthState
import com.omaraboesmail.bargain.pojo.DbCRUDState
import com.omaraboesmail.bargain.pojo.User
import com.omaraboesmail.bargain.ui.mainActivity.MainActivity
import com.omaraboesmail.bargain.ui.signInActivity.SignInActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        haveAccount.setOnClickListener {
            NavigationFlow(this).navigateActivity(SignInActivity())
        }
        signUpBtn.setOnClickListener {
            validateAll()
        }

    }

    private fun validateAll(): Boolean {
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

    private fun getData(): User =
        User(
            id = "",
            email = email.editText!!.text.toString(),
            name = name.editText!!.text.toString(),
            phone = phone.editText!!.text.toString(),
            password = password.editText!!.text.toString(),
            address = address.editText!!.text.toString(),
            photoUrl = "",
            approved = false,
            nationalId = "",
            trustPoints = 10
        )


    private fun register() {

        signUpViewModel.signUp(getData())

        signUpViewModel.userState().observe(this, Observer {
            when (it) {
                AuthState.LOADING ->
                    Toast.makeText(this, AuthState.LOADING.msg, Toast.LENGTH_LONG).show()
                AuthState.FAILED ->
                    Toast.makeText(this, AuthState.FAILED.msg, Toast.LENGTH_LONG).show()
                AuthState.SUCCESS -> insertUserState()
                else -> Toast.makeText(this, AuthState.EXCEPTION.msg, Toast.LENGTH_LONG).show()

            }
        })

    }


    private fun insertUserState() {
        signUpViewModel.operationStat().observe(this, Observer {
            when (it) {
                DbCRUDState.LOADING ->
                    Toast.makeText(this, AuthState.LOADING.msg, Toast.LENGTH_LONG).show()
                DbCRUDState.FAILED ->
                    Toast.makeText(this, AuthState.FAILED.msg, Toast.LENGTH_LONG).show()
                DbCRUDState.INSERTED -> NavigationFlow(this).navigateActivity(MainActivity())
                else -> Toast.makeText(this, AuthState.EXCEPTION.msg, Toast.LENGTH_LONG).show()

            }
        })
    }


    private fun validatePassword(): Boolean {
        if (password.editText!!.text.isValidPassword()) {
            password.error = null
            return true
        } else {
            password.error = "please enter valid Password"
            return false
        }
    }

    private fun conPassword(): Boolean {
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
            this.error = "please enter valid data"
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
    !isNullOrEmpty() && (this!!.length > 6)

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this!!).matches() && this.isValidInput()

fun CharSequence?.isValidPassword() =
    !isNullOrEmpty() && this!!.length > 8

