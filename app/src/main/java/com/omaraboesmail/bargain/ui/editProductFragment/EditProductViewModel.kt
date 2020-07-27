package com.omaraboesmail.bargain.ui.editProductFragment

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.Prodruct.ProductRepo
import com.omaraboesmail.bargain.data.individuals.IndividualsRepo
import com.omaraboesmail.bargain.pojo.IndividualProduct

class EditProductViewModel : ViewModel() {
    fun getProductClicked() = IndividualsRepo.getClickedProduct()
    fun updateProduct(product: IndividualProduct) = IndividualsRepo.updateProduct(product)
    fun uploadImage(file: Uri, product: IndividualProduct) =
        ProductRepo.uploadIndividualProductImage(file, product)

    val imageStat = ProductRepo.uploadProgress
}