package com.omaraboesmail.bargain.ui.mainActivity.supreMarketDetaills

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.SuperMarketRepo
import com.omaraboesmail.bargain.pojo.SuperMarket

class SuperMarketDetailsViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    fun getClickedMarket(): LiveData<SuperMarket> {
        return SuperMarketRepo.SuperMarketByName

    }

    fun getCachedMarketName(): String? {
        return SuperMarketRepo.marketName.value

    }
}
