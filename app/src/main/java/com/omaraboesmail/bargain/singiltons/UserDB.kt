package com.omaraboesmail.bargain.singiltons

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.pojo.DbCRUDState
import com.omaraboesmail.bargain.pojo.User
import com.omaraboesmail.bargain.singiltons.FireBaseAuthenticate.fbUser


object UserDB {
    private val db = FirebaseFirestore.getInstance()
    val dbCRUDState = MutableLiveData<DbCRUDState>().apply { value = DbCRUDState.LOADING }

    val usersColl = db.collection("users")
    var currant = MutableLiveData<User>().apply { getUserData() }
    val userRepo = UserRepo()
    lateinit var userRef: DocumentReference

    fun getUserData(): MutableLiveData<User> {
        try {
            userRef = fbUser!!.uid.let { usersColl.document(it) }
            usersColl.whereEqualTo("id", fbUser!!.uid).get().addOnSuccessListener {
                if (it != null) {
                    userRef = usersColl.document(it.documents[0].id)
                    Log.e(
                        "OOOOOOOOOOOOO",
                        it.documents[0].data.toString() + "\n" +
                                userRef.id
                    )
                    currant.value = fromMapToObject(it.documents[0].data!!)

                }
            }
                .addOnFailureListener {
                    Log.e("OOOOOOOOOOOOO", it.message.toString())

                }

            return currant

        } catch (e: Exception) {
            currant.value = null
            return currant

        }
    }

    fun fromMapToObject(map: Map<String, Any>): User {
        return User(
            id = map.get("id").toString(),
            email = map.get("email").toString(),
            name = map.get("name").toString(),
            phone = map.get("phone").toString(),
            password = map.get("password").toString(),
            address = map.get("address").toString(),
            photoUrl = map.get("photoUrl").toString(),
            approved = map.get("approved").toString().toBoolean(),
            nationalId = map.get("nationalId").toString(),
            trustPoints = map.get("trustPoints").toString().toInt()
        )

    }

}

