package com.omaraboesmail.bargain.ui.mainActivity.StoresTab.supreMarketDetaills

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import com.omaraboesmail.bargain.data.stores.SuperMarketRepo
import com.omaraboesmail.bargain.pojo.SuperMarket


class SuperMarketDetailsViewModel : ViewModel() {

    fun getClickedMarket(): LiveData<SuperMarket> {
        return SuperMarketRepo.SuperMarketByName

    }


    fun getCachedMarketName(): String? {
        return SuperMarketRepo.marketName.value

    }

    fun setName(name: String) {
        return SuperMarketRepo.setMarketName(name)

    }
}
