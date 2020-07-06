package com.omaraboesmail.bargain.ui.mainActivity.addSuperMarketFragment

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.SuperMarketRepo
import com.omaraboesmail.bargain.pojo.SuperMarket

class AddSuperMarketViewModel : ViewModel() {

    fun addMarket(image: Uri, superMarket: SuperMarket) =
        SuperMarketRepo.createMarket(image, superMarket)

    fun uploadProgress() = SuperMarketRepo.uploadProgress
    fun insertstate() = SuperMarketRepo.crudState
    fun uploadStatus() = SuperMarketRepo.uploadStat
}
