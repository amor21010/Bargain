package com.omaraboesmail.bargain.pojo

import com.google.firebase.firestore.DocumentReference

open class Store(
    open val id: String,
    open val name: String,
    open val address: String,
    open val phone: String,
    open val discount: Float,
    open var products: List<DocumentReference> = ArrayList()
)
