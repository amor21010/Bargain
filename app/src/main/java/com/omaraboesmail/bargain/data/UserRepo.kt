package com.omaraboesmail.bargain.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.omaraboesmail.bargain.pojo.AuthState
import com.omaraboesmail.bargain.pojo.DbCRUDState
import com.omaraboesmail.bargain.pojo.User
import com.omaraboesmail.bargain.pojo.UserVerState
import com.omaraboesmail.bargain.singiltons.FireBaseAuthenticate.authState
import com.omaraboesmail.bargain.singiltons.FireBaseAuthenticate.fbUser
import com.omaraboesmail.bargain.singiltons.FireBaseAuthenticate.firebaseAuth
import com.omaraboesmail.bargain.singiltons.FireBaseAuthenticate.isUserVerified
import com.omaraboesmail.bargain.singiltons.UserDB.dbCRUDState
import com.omaraboesmail.bargain.singiltons.UserDB.userRef
import com.omaraboesmail.bargain.singiltons.UserDB.usersColl


class UserRepo {


    fun insertUserToFireStore(user: User) {
        usersColl.add(user)
            .addOnSuccessListener {
                dbCRUDState.value = DbCRUDState.INSERTED

            }
            .addOnFailureListener {
                dbCRUDState.value = DbCRUDState.FAILED
            }
    }

    fun signIn(email: String, password: String) {
        Log.d("oooooooo", "signIn()")
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("oooooooo", "signIn() suc")

                authState.value = AuthState.SUCCESS
                fbUser = firebaseAuth.currentUser
                if (fbUser != null && fbUser!!.isEmailVerified)
                    isUserVerified.value = UserVerState.VERIFIED
            } else {
                Log.d("oooooooo", "signIn() fa")

                authState.value = AuthState.UNAUTHORIZED
            }
        }

    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun updateFireStoreUser(user: User) {
        Log.d("ooooooooo", "UPDATing")
        userRef.update(fromUserToMap(user))
            .addOnSuccessListener {
                Log.d("ooooooooo", "UPDATED")
                dbCRUDState.value = DbCRUDState.UPDATED
            }
            .addOnFailureListener {
                Log.d("ooooooooo", "FAILED" + it.message)
                dbCRUDState.value = DbCRUDState.FAILED
            }
    }

    fun signUp(user: User) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    authState.value = AuthState.SUCCESS
                    if (fbUser != null) {
                        user.id = fbUser!!.uid
                        sendVerificationEmail(user)
                    }
                } else {
                    AuthState.FAILED.msg = task.exception.toString().substringAfterLast(":")
                    authState.value = AuthState.FAILED
                }

            }
    }


    fun sendVerificationEmail(user: User) {
        fbUser?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (fbUser != null) {
                        user.approved = fbUser!!.isEmailVerified
                        insertUserToFireStore(user)
                    }
                } else {
                    Log.d("sendEmail", task.exception.toString())
                }
            }
    }

    fun isVerified(): LiveData<Boolean> = MutableLiveData(fbUser?.isEmailVerified)
    fun fromUserToMap(user: User): HashMap<String, Any> {

        return hashMapOf(
            "id" to user.id,
            "email" to user.email,
            "name" to user.name,
            "phone" to user.phone,
            "password" to user.password,
            "address" to user.address,
            "nationalId" to user.nationalId,
            "photoUrl" to user.photoUrl,
            "approved" to user.approved,
            "trustPoints" to user.trustPoints
        )

    }

}



