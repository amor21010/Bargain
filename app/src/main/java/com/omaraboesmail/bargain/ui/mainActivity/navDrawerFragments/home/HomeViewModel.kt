package com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.home

import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.OffersRepo
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.data.UserRepo.currant
import com.omaraboesmail.bargain.data.individuals.IndividualsRepo
import com.omaraboesmail.bargain.data.stores.SuperMarketRepo
import com.omaraboesmail.bargain.pojo.User

class HomeViewModel : ViewModel() {
    fun isUserEmailVerified() = UserRepo.isUserEmailVerified
    fun updateUser(user: User) = UserRepo.updateFireStoreUser(user)
    val getCurrentUserData = currant
    fun getBanners() = OffersRepo.getAllOffersBanners()
    fun getSuperMarketsDiscount() = SuperMarketRepo.getMarketsWithDiscount()
    fun getIndividualsDiscount(type: String) = IndividualsRepo.getIndividualDiscount(type)
}
