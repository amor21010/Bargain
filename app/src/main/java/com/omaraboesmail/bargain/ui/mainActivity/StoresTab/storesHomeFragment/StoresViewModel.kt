package com.omaraboesmail.bargain.ui.mainActivity.StoresTab.storesHomeFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.pojo.StoreType

class StoresViewModel : ViewModel() {

    var data: MutableLiveData<List<StoreType>> = MutableLiveData<List<StoreType>>()


}
