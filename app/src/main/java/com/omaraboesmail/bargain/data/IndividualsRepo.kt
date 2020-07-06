package com.omaraboesmail.bargain.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.DocumentReference
import com.omaraboesmail.bargain.pojo.IndividualProduct
import com.omaraboesmail.bargain.pojo.toHashMap
import com.omaraboesmail.bargain.pojo.toIndividualProduct
import com.omaraboesmail.bargain.utils.Const.TAG

object IndividualsRepo : IndividualsInterface {
    lateinit var productDR: DocumentReference
    override fun getAllProductsByType(Type: String): LiveData<List<IndividualProduct>> {
        return object : LiveData<List<IndividualProduct>>() {
            override fun onActive() {
                super.onActive()
                Log.d(TAG, "$Type this is type")
                FireBaseConst.individualsDB(Type).whereEqualTo("type", Type)
                    .whereEqualTo("removed", false)
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null) return@addSnapshotListener
                        if (snapshot != null) {
                            val individualProducts = ArrayList<IndividualProduct>()
                            snapshot.documents.forEach { documentSnapshot ->
                                documentSnapshot.data?.toIndividualProduct()?.let { product ->
                                    individualProducts.add(
                                        product
                                    )
                                }

                            }
                            value = individualProducts
                        }
                    }
            }
        }

    }


    override fun insertProduct(product: IndividualProduct) {
        FireBaseConst.individualsDB(product.type).add(product).addOnSuccessListener {
            Log.d(TAG, it.toString())
        }

    }

    override fun updateProductByName(newProduct: IndividualProduct) {
        FireBaseConst.individualsDB(newProduct.type).whereEqualTo("name", newProduct.name).get()
            .addOnSuccessListener {
                if (it.documents[0] != null) productDR = it.documents[0].reference
                productDR.update(newProduct.toHashMap()).addOnSuccessListener {
                    Log.d(TAG, it.toString())

                }
            }
    }

    override fun removeProductByName(product: IndividualProduct) {
        FireBaseConst.individualsDB(product.type).whereEqualTo("name", product.name).get()
            .addOnSuccessListener {
                if (it.documents[0] != null) productDR = it.documents[0].reference
                product.removed = true
                productDR.update(product.toHashMap()).addOnSuccessListener {
                    Log.d(TAG, it.toString())

                }
            }
    }
}