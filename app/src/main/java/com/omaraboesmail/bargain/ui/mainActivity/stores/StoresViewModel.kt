package com.omaraboesmail.bargain.ui.mainActivity.stores

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.pojo.StoreType

class StoresViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var data: MutableLiveData<List<StoreType>> = MutableLiveData<List<StoreType>>()


}
