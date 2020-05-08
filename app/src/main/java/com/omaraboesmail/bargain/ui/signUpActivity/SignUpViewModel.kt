package com.omaraboesmail.bargain.ui.signUpActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.pojo.AuthState
import com.omaraboesmail.bargain.pojo.DbCRUDState
import com.omaraboesmail.bargain.pojo.User
import com.omaraboesmail.bargain.singiltons.FireBaseAuthenticate.authState
import com.omaraboesmail.bargain.singiltons.UserDB.dbCRUDState


class SignUpViewModel : ViewModel() {

    private val userRepo = UserRepo()


    fun signUp(user: User) = userRepo.signUp(user)
    fun sendEmailVerification(user: User) = userRepo.sendVerificationEmail(user)
    fun insertUserToFireStore(user: User) = userRepo.insertUserToFireStore(user)

    fun userState(): LiveData<AuthState> {
        return authState
    }


    fun operationStat(): LiveData<DbCRUDState> {
        return dbCRUDState
    }


}