package com.omaraboesmail.bargain.ui.signUpActivity


import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputLayout
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.FireBaseConst.firebaseAuthInstance
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.pojo.User
import com.omaraboesmail.bargain.resultStats.AuthState
import com.omaraboesmail.bargain.resultStats.DbCRUDState
import com.omaraboesmail.bargain.ui.mainActivity.MainActivity
import com.omaraboesmail.bargain.ui.signInActivity.SignInActivity
import com.omaraboesmail.bargain.utils.*
import com.omaraboesmail.bargain.utils.DialogMaker.authDialog
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private val signUpViewModel: SignUpViewModel by viewModels()

    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        DialogMaker.mContext = this
        dialog = authDialog()
        haveAccount.setOnClickListener {
            NavigationFlow(this).navigateActivity(SignInActivity())
        }
        signUpBtn.setOnClickListener {
            if (validateAll()) register(getData())
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


            return true
        }

    }

    private fun getData(): User =
        User(
            id = "",
            email = email.getText()!!,
            name = name.getText()!!,
            phone = phone.getText()!!,
            password = password.getText()!!,
            address = address.getText()!!,
            photoUrl = "",
            approved = false,
            nationalId = "",
            trustPoints = 10,
            isAdmin = false,
            isSeller = false
        )


    private fun register(user: User) {

        signUpViewModel.signUp(user)

        signUpViewModel.userState().observe(this, Observer {
            when (it) {
                AuthState.LOADING ->

                    showDialog(it.msg, true)
                AuthState.FAILED ->
                    showDialog(it.msg, false)
                AuthState.SUCCESS -> {
                    UserRepo.setFirebaseUser(firebaseAuthInstance.currentUser)

                    insertUserState()
                }
                else -> Toast.makeText(this, AuthState.EXCEPTION.msg, Toast.LENGTH_LONG).show()

            }
        })

    }


    private fun insertUserState() {
        signUpViewModel.operationStat().observe(this, Observer {
            when (it) {
                DbCRUDState.LOADING ->
                    Toast.makeText(this, DbCRUDState.LOADING.msg, Toast.LENGTH_LONG).show()
                DbCRUDState.FAILED ->
                    Toast.makeText(this, DbCRUDState.FAILED.msg, Toast.LENGTH_LONG).show()
                DbCRUDState.INSERTED -> {

                    NavigationFlow(this).navigateActivity(MainActivity())

                }
                else -> Toast.makeText(this, DbCRUDState.EXCEPTION.msg, Toast.LENGTH_LONG).show()

            }
        })
    }


    private fun validatePassword(): Boolean {
        return if (password.editText!!.text.isValidPassword()) {
            password.error = null
            true
        } else {
            password.error = "please enter valid Password"
            false
        }
    }

    private fun conPassword(): Boolean {
        return if (password.editText!!.text.isValidPassword()) {
            if (password.editText!!.text == confPassword.editText!!.text)
                confPassword.error = null
            true
        } else {
            confPassword.error = "password dose not match"
            false
        }
    }

    private fun TextInputLayout.validateInput(): Boolean {
        val editText = this.editText
        return if (editText!!.text.isValidInputAndNotShort()) {
            this.error = null
            true
        } else {
            this.error = "please enter valid data"
            false
        }
    }

    private fun validateEmail(): Boolean {
        return if (email.editText!!.text.isValidEmail()) {
            email.error = null
            true
        } else {
            email.error = "please enter valid Password"
            false
        }
    }

    private fun showDialog(msg: String, boolean: Boolean) {
        DialogMaker.loading.value = boolean
        DialogMaker.mTitle.value = msg
        dialog.show()

    }


}

