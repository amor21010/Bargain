package com.omaraboesmail.bargain.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.omaraboesmail.bargain.data.FireBaseConst.authState
import com.omaraboesmail.bargain.data.FireBaseConst.dbCRUDState
import com.omaraboesmail.bargain.data.FireBaseConst.firebaseAuthInstance
import com.omaraboesmail.bargain.data.FireBaseConst.usersColl
import com.omaraboesmail.bargain.pojo.User
import com.omaraboesmail.bargain.pojo.toHashMap
import com.omaraboesmail.bargain.pojo.toUserObject
import com.omaraboesmail.bargain.resultStats.AuthState
import com.omaraboesmail.bargain.resultStats.DbCRUDState
import com.omaraboesmail.bargain.resultStats.UserVerState
import com.omaraboesmail.bargain.utils.Const.TAG


object UserRepo {
    lateinit var userRef: DocumentReference


    val fbUserLive: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val currant: LiveData<User> = Transformations.switchMap(fbUserLive) {
        it?.let { getUserData(it) }
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
        authState.value = AuthState.LOADING
        firebaseAuthInstance.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    authState.value = AuthState.SUCCESS
                    fbUserLive.value = firebaseAuthInstance.currentUser
                } else {
                    authState.value = AuthState.UNAUTHORIZED
                    Log.d(TAG, "FAILED" + task.exception!!.message)

                }
            }

    }


    private fun getUserData(firebaseUser: FirebaseUser): LiveData<User> {
        return object : LiveData<User>() {
            override fun onActive() {
                super.onActive()
                usersColl.whereEqualTo("email", firebaseUser.email).addSnapshotListener { it, e ->
                    if (e != null) return@addSnapshotListener
                    if (it != null && !it.documents.isNullOrEmpty()) {
                        userRef = (it.documents.first().reference)
                        Log.e(
                            TAG,
                            it.documents.first().data.toString() + "\n" +
                                    userRef.id + " / " + it.first().id
                        )
                        value = it.documents.first().data?.toUserObject()
                    }
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
        userRef.update(user.toHashMap())
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
        authState.value = AuthState.LOADING

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



