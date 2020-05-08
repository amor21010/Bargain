package com.omaraboesmail.bargain.singiltons

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.omaraboesmail.bargain.pojo.AuthState
import com.omaraboesmail.bargain.pojo.UserVerState

object FireBaseAuthenticate {
    val firebaseAuth = FirebaseAuth.getInstance()
    val authState = MutableLiveData<AuthState>().apply { value = AuthState.LOADING }
    val isUserVerified = MutableLiveData<UserVerState>().apply { value = UserVerState.UNVERIFIED }
    var fbUser: FirebaseUser? = firebaseAuth.currentUser

}
