package com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.orderHistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderHistoryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is History Fragment"
    }
    val text: LiveData<String> = _text
}