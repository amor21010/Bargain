package com.omaraboesmail.bargain.ui.signUpActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.pojo.User
import com.omaraboesmail.bargain.resultStats.AuthState
import com.omaraboesmail.bargain.resultStats.DbCRUDState
import com.omaraboesmail.bargain.singiltons.FireBaseAuthenticate.authState
import com.omaraboesmail.bargain.singiltons.FireBaseAuthenticate.firebaseAuthInstance
import com.omaraboesmail.bargain.singiltons.UserDB.dbCRUDState


class SignUpViewModel : ViewModel() {


    fun signUp(user: User) = UserRepo.signUp(user)
    fun sendEmailVerification() = UserRepo.sendVerificationEmail(firebaseAuthInstance.currentUser!!)
    fun insertUserToFireStore(user: User) = UserRepo.insertUserToFireStore(user)

    fun userState(): LiveData<AuthState> {
        return authState
    }


    fun operationStat(): LiveData<DbCRUDState> {
        return dbCRUDState
    }


}