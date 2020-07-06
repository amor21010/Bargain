package com.omaraboesmail.bargain.ui.mainActivity.vegetablesFragment

import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.VegetableRepo

class VegetablesViewModel : ViewModel() {
    fun getVegetableList() = VegetableRepo.getVegetableList()
}
