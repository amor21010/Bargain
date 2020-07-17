package com.omaraboesmail.bargain.ui.mainActivity.StoresTab.vegetablesFragment

import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.stores.VegetableRepo

class VegetablesViewModel : ViewModel() {
    fun getVegetableList() = VegetableRepo.getVegetableList()
}
