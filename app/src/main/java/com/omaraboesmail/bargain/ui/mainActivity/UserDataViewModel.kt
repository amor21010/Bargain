package com.omaraboesmail.bargain.ui.mainActivity

import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.UserRepo

class UserDataViewModel : ViewModel() {
    fun signOut() = UserRepo.signOut()


}