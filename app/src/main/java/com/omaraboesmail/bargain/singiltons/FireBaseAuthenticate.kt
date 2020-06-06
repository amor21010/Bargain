package com.omaraboesmail.bargain.singiltons

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.omaraboesmail.bargain.resultStats.AuthState

object FireBaseAuthenticate {
    val firebaseAuthInstance = FirebaseAuth.getInstance()
    val authState = MutableLiveData<AuthState>().apply { value = AuthState.LOADING }

}
