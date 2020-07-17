package com.omaraboesmail.bargain.ui.mainActivity.IndividualsTab.homeService

import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.data.individuals.IndividualsRepo

class HomeServiceViewModel : ViewModel() {
    fun getAllByType(type: String) = IndividualsRepo.getAllProductsByType(type)
    fun getUser() = UserRepo.currant
}