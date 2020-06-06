package com.omaraboesmail.bargain.ui.mainActivity.superMarkets

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.SuperMarketRepo
import com.omaraboesmail.bargain.pojo.SuperMarket

class SuperMarketViewModel : ViewModel() {
    // TODO: Implement the ViewModel


    fun getAllSuperMarkets(): LiveData<List<SuperMarket>> = SuperMarketRepo.getAllMarkets()
}