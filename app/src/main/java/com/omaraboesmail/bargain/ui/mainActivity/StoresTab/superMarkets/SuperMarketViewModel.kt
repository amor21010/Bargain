package com.omaraboesmail.bargain.ui.mainActivity.StoresTab.superMarkets

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.data.stores.SuperMarketRepo
import com.omaraboesmail.bargain.pojo.SuperMarket

class SuperMarketViewModel : ViewModel() {

    fun getAllSuperMarkets(): LiveData<List<SuperMarket>> = SuperMarketRepo.getAllMarkets()
    fun getUser() = UserRepo.currant
}
