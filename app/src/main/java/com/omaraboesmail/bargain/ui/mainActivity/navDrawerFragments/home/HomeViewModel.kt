package com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.pojo.UserVerState
import com.omaraboesmail.bargain.singiltons.FireBaseAuthenticate.isUserVerified

class HomeViewModel : ViewModel() {


    val text: MutableLiveData<UserVerState> = isUserVerified

}
