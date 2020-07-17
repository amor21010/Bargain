package com.omaraboesmail.bargain.data.stores

import android.util.Log
import androidx.lifecycle.LiveData
import com.omaraboesmail.bargain.data.FireBaseConst.vegetableDB
import com.omaraboesmail.bargain.pojo.VegetableProduct
import com.omaraboesmail.bargain.pojo.toVegetableProduct
import com.omaraboesmail.bargain.utils.Const.TAG

object VegetableRepo {

    fun getVegetableList(): LiveData<List<VegetableProduct>> {
        return object : LiveData<List<VegetableProduct>>() {
            override fun onActive() {
                super.onActive()
                val productList = ArrayList<VegetableProduct>()
                vegetableDB.addSnapshotListener { snapShot, e ->
                    if (e != null) return@addSnapshotListener
                    if (snapShot != null) {
                        val it = snapShot.documents
                        if (it.isNotEmpty()) {
                            for (product in it) {
                                Log.d(TAG, product.data?.toVegetableProduct().toString())
                                product.data?.toVegetableProduct()
                                    ?.let { it1 -> productList.add(it1) }

                            }

                            value = (productList)
                        }
                    }


                }
            }
        }
    }

    fun addVegetable(vegetableProduct: VegetableProduct) {
        vegetableDB.add(vegetableProduct).addOnSuccessListener {
            getVegetableList()
        }
    }
}