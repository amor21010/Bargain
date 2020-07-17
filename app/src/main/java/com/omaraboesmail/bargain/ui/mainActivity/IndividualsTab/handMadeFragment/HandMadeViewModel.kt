package com.omaraboesmail.bargain.ui.mainActivity.IndividualsTab.handMadeFragment

import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.data.individuals.IndividualsRepo

class HandMadeViewModel : ViewModel() {
    fun getAllByType(type: String) = IndividualsRepo.getAllProductsByType(type)
    fun getUser() = UserRepo.currant
}