package com.omaraboesmail.bargain.ui.mainActivity.IndividualsTab.AddIndividualProductFragment

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.Prodruct.ProductRepo
import com.omaraboesmail.bargain.data.individuals.IndividualsRepo
import com.omaraboesmail.bargain.pojo.IndividualProduct

class AddIndividualProductViewModel : ViewModel() {
    fun addIndividualProduct(product: IndividualProduct) = IndividualsRepo.insertProduct(product)
    fun uploadPhoto(uri: Uri, individualProduct: IndividualProduct) =
        ProductRepo.uploadIndividualProductImage(uri, individualProduct)

    val imageStat = ProductRepo.uploadProgress
    // TODO: Implement the ViewModel
}