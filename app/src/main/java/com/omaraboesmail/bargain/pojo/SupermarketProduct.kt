package com.omaraboesmail.bargain.pojo

open class SupermarketProduct(
    override val name: String,
    override val price: String,
    val quantityAvailable: Int,
    override val unit: String,
    override var seller: String,
    override val type: String
) : Product(name, price, "superMarket", type, seller, unit)


fun Map<String, Any>.toSuperMarketProduct(): SupermarketProduct {
    return SupermarketProduct(
        this["name"].toString(),
        this["price"].toString(),
        this["quantityAvilable"].toString().toInt(),
        this["unit"].toString(),
        this["seller"].toString(),
        this["type"].toString()

    )

}






