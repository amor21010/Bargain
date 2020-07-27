package com.omaraboesmail.bargain.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.omaraboesmail.bargain.utils.Const.TAG
import com.omaraboesmail.bargain.utils.checkItemsAre
import kotlin.String as String1

object OffersRepo {

    fun getAllOffersBanners(): LiveData<List<String1>> {
        return object : LiveData<List<String1>>() {
            override fun onActive() {
                super.onActive()
                FireBaseConst.offersBanners.addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        Log.d(TAG, "onActive: $exception")
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        val list =
                            (snapshot.documents[0]["urls"] as ArrayList<*>).checkItemsAre<kotlin.String>()
                        value = list
                    }
                }
            }
        }
    }
}