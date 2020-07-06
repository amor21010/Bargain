package com.omaraboesmail.bargain.data

import androidx.lifecycle.LiveData
import com.omaraboesmail.bargain.pojo.IndividualProduct

interface IndividualsInterface {
    fun getAllProductsByType(Type: String): LiveData<List<IndividualProduct>>
    fun insertProduct(product: IndividualProduct)
    fun updateProductByName(newProduct: IndividualProduct)
    fun removeProductByName(product: IndividualProduct)
}