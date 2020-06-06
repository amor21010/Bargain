package com.omaraboesmail.bargain.ui.signInActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.resultStats.AuthState
import com.omaraboesmail.bargain.singiltons.FireBaseAuthenticate.authState


class SignInViewModel : ViewModel() {

    fun signIn(email: String, password: String) = UserRepo.signIn(email, password)
    fun isAuthorized(): LiveData<AuthState> = authState
}