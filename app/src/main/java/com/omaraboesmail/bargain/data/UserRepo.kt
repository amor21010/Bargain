package com.omaraboesmail.bargain.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.omaraboesmail.bargain.pojo.ToMap
import com.omaraboesmail.bargain.pojo.User
import com.omaraboesmail.bargain.pojo.toUserObject
import com.omaraboesmail.bargain.resultStats.AuthState
import com.omaraboesmail.bargain.resultStats.DbCRUDState
import com.omaraboesmail.bargain.resultStats.UserVerState
import com.omaraboesmail.bargain.singiltons.FireBaseAuthenticate.authState
import com.omaraboesmail.bargain.singiltons.FireBaseAuthenticate.firebaseAuthInstance
import com.omaraboesmail.bargain.singiltons.UserDB.db
import com.omaraboesmail.bargain.singiltons.UserDB.dbCRUDState
import com.omaraboesmail.bargain.utils.Const.TAG


object UserRepo {
    lateinit var userRef: DocumentReference


    val fbUserLive: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val currant: LiveData<User> = Transformations.switchMap(fbUserLive) {
        getUserData(it)
    }

    val isUserEmailVerified: LiveData<UserVerState> = Transformations.switchMap(fbUserLive) {
        it.isVerified()
    }

    fun setFirebaseUser(firebaseUser: FirebaseUser?) {
        if (firebaseUser != fbUserLive.value) fbUserLive.value = firebaseUser
        else fbUserLive.value?.reload()
    }

    fun FirebaseUser?.isVerified(): LiveData<UserVerState> {
        return object : LiveData<UserVerState>() {
            override fun onActive() {
                super.onActive()
                if (this@isVerified != null) {
                    val firebaseUser: FirebaseUser = this@isVerified
                    value = if (firebaseUser.isEmailVerified) {
                        UserVerState.VERIFIED
                    } else UserVerState.UNVERIFIED
                }
            }
        }

    }

    val usersColl = db.collection("users")


    fun insertUserToFireStore(user: User) {
        usersColl.add(user)
            .addOnSuccessListener {
                dbCRUDState.value = DbCRUDState.INSERTED
                Log.d(TAG, "inserted" + it.id)
                sendVerificationEmail(firebaseAuthInstance.currentUser!!)

            }
            .addOnFailureListener {
                dbCRUDState.value = DbCRUDState.FAILED
                Log.d(TAG, "FAILED to insert" + it.message)
            }
    }

    fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn()")
        firebaseAuthInstance.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signIn() suc")

                    authState.value = AuthState.SUCCESS
                    fbUserLive.value = firebaseAuthInstance.currentUser

                } else {
                    Log.d(TAG, "signIn() fa")
                    authState.value = AuthState.UNAUTHORIZED
                    Log.d(TAG, "FAILED" + task.exception!!.message)

                }
            }

    }


    private fun getUserData(firebaseUser: FirebaseUser?): LiveData<User> {
        return object : LiveData<User>() {
            override fun onActive() {
                super.onActive()
                usersColl.whereEqualTo("email", firebaseUser?.email).get().addOnSuccessListener {
                    if (it != null) {
                        userRef = (it.documents[0].reference)
                        Log.e(
                            "OOOOOOOOOOOOO",
                            it.documents[0].data.toString() + "\n" +
                                    userRef.id + " / " + it.documents[0].id
                        )
                        value = it.documents[0].data?.toUserObject()
                    }
                }
                    .addOnFailureListener {
                        Log.e("OOOOOOOOOOOOO", it.message.toString())
                        value = null
                    }
            }
        }
    }


    fun signOut() {

        firebaseAuthInstance.signOut()
        authState.value = AuthState.LOADING
        setFirebaseUser(null)

    }

    fun updateFireStoreUser(user: User) {
        Log.d(TAG, "UPDATing")
        userRef.update(user.ToMap())
            .addOnSuccessListener {
                Log.d(TAG, "UPDATED")
                dbCRUDState.value = DbCRUDState.UPDATED
            }
            .addOnFailureListener {
                Log.d(TAG, "FAILED to Update" + it.message)
                dbCRUDState.value = DbCRUDState.FAILED
            }
    }

    fun signUp(user: User) {
        firebaseAuthInstance.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    authState.value = AuthState.SUCCESS
                    Log.d(TAG, "Signed up")
                    insertUserToFireStore(user)
                } else {
                    AuthState.FAILED.msg = task.exception.toString().substringAfterLast(":")
                    authState.value = AuthState.FAILED
                    Log.d(TAG, "FAILED to Sign Up" + task.exception!!.message)
                }

            }
    }


    fun sendVerificationEmail(firebaseUser: FirebaseUser) {
        Log.d(TAG, "email sending")

        firebaseUser.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "email sent")
                } else {
                    Log.d(TAG, "email failed " + task.exception.toString())
                }
            }
    }


}



