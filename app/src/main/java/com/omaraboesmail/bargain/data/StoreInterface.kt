package com.omaraboesmail.bargain.data

import androidx.lifecycle.LiveData
import com.omaraboesmail.bargain.pojo.Store

interface StoreInterface {
    fun getAllStores(): LiveData<List<Store>>
    fun getStoreByName(name: String): LiveData<Store>?
}