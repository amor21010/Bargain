package com.omaraboesmail.bargain.singiltons

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.omaraboesmail.bargain.resultStats.DbCRUDState


object UserDB {
    var db = FirebaseFirestore.getInstance()
    val dbCRUDState = MutableLiveData<DbCRUDState>().apply { value = DbCRUDState.LOADING }


}

