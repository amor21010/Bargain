package com.omaraboesmail.bargain.pojo

data class VegetableProduct(
    override val name: String,
    override val price: String,
    val quantityAvailable: Int,
    override val unit: String,
    override val type: String
) : Product(name, price, "vegetable", type, "", unit)

fun Map<String, Any>.toVegetableProduct(): VegetableProduct {
    return VegetableProduct(
        this["name"].toString(),
        this["price"].toString(),
        this["quantityAvailable"].toString().toInt(),
        this["unit"].toString(),
        this["type"].toString()

    )

}


