package com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.data.UserRepo.currant
import com.omaraboesmail.bargain.pojo.User

class HomeViewModel : ViewModel() {


    fun isUserEmailVerified() = UserRepo.isUserEmailVerified

    fun updateUser(user: User) = UserRepo.updateFireStoreUser(user)
    fun signIn(user: User) = UserRepo.signIn(user.email, user.password)

    val getCurrentUserData = currant

    fun setFirebaseUser(firebaseUser: FirebaseUser) {
        UserRepo.fbUserLive.value = firebaseUser
    }

    fun updateUserData(): LiveData<User> = (currant)


}
