package com.omaraboesmail.bargain.pojo

data class SuperMarket(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val discount: Float,
    var photo: String
)

fun Map<String, Any>.toSuperMarket(): SuperMarket? {
    return SuperMarket(
        this["id"].toString(),
        this["name"].toString(),
        this["address"].toString(),
        this["phone"].toString(),
        this["discount"].toString().toFloat(),
        this["photo"].toString()
    )
}

fun SuperMarket.toHashMap(): HashMap<String, Any> {

    return hashMapOf(
        "id" to this.id,
        "name" to this.name,
        "phone" to this.phone,
        "address" to this.address,
        "discount" to this.discount,
        "photo" to this.photo
    )

}