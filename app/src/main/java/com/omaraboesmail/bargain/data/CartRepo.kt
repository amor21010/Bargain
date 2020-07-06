package com.omaraboesmail.bargain.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.firebase.firestore.DocumentReference
import com.omaraboesmail.bargain.pojo.Cart
import com.omaraboesmail.bargain.pojo.Product
import com.omaraboesmail.bargain.pojo.toCartObject
import com.omaraboesmail.bargain.utils.Const.TAG
import com.omaraboesmail.bargain.utils.checkItemsAre

object CartRepo {
    val cartProductsList: MutableLiveData<ArrayList<Product>> = MutableLiveData()
    val cartProductsListSize = MutableLiveData<Int>()
    lateinit var cartReference: DocumentReference

    var cart: Cart = Cart(UserRepo.fbUserLive.value?.email!!, arrayListOf())
    fun getCartListSize() = Transformations.switchMap(cartProductsListSize) {
        getCartSize()
    }


    fun addToCart(product: Product, quantity: Int = 1) {
        if (!cartProductsList.value.isNullOrEmpty()) {
            val productList = cartProductsList.value
            val productNames = ArrayList<String>()
            if (!productList.isNullOrEmpty()) {
                productList.forEach {
                    productNames.add(it.name)
                }
                if (productNames.contains(product.name)) {
                    val flag = productNames.indexOf(product.name)
                    productList[flag].quantityOrdered += quantity
                } else {
                    product.quantityOrdered = quantity
                    productList.add(product)
                }
                cart.products = productList
                updateOnlineCart(productList)
            }
        } else {
            product.quantityOrdered = quantity

            cartProductsList.value?.add(product)
            try {
                updateOnlineCart(cartProductsList.value!!)
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
                cart.products = arrayListOf(product)
                createOnlineCart(cart)
            }
        }
        getOnlineCart(cart.email)

    }

    fun getCartSize(): LiveData<Int> {
        var int = 0
        cart.products.forEach { int += it.quantityOrdered }
        return MutableLiveData(int)
    }

    fun getOnlineCart(email: String): LiveData<Cart> {
        return object : LiveData<Cart>() {
            override fun onActive() {
                super.onActive()
                FireBaseConst.cartsDB.whereEqualTo("email", email)
                    .addSnapshotListener { snapshot, e ->
                        if (e != null) {
                            return@addSnapshotListener
                        }
                        if (snapshot != null) {
                            val documentsList = snapshot.documents
                            if (!documentsList.isNullOrEmpty()) {
                                cartReference = (documentsList[0].reference)
                                cart = documentsList[0].data?.toCartObject()!!
                                val productList = cart.products.checkItemsAre<Product>()
                                cartProductsList.value = productList
                                cartProductsListSize.value = productList.size
                                value = cart
                            }
                        }

                    }

            }
        }
    }

    private fun createOnlineCart(cart: Cart) {
        FireBaseConst.cartsDB.add(cart).addOnSuccessListener {
            this.cart = cart
            cartReference = it
            cartProductsList.value = cart.products
            Log.d(TAG, it.toString())
        }
    }

    fun updateOnlineCart(product: ArrayList<Product>) {
        val cart = Cart(products = product)
        cartReference.set(cart).addOnSuccessListener {
            getOnlineCart(cart.email)
        }
    }


}


