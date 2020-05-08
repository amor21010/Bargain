package com.omaraboesmail.bargain.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.pojo.User

class UserDataViewModel : ViewModel() {
    private val userRepo = UserRepo()

    fun isUserEmailVerified(): LiveData<Boolean> = userRepo.isVerified()
    fun updateUser(user: User) = userRepo.updateFireStoreUser(user)
    fun signOut() = userRepo.signOut()

}