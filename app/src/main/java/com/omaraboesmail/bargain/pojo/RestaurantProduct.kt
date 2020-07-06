package com.omaraboesmail.bargain.pojo

data class RestaurantProduct(
    override val name: String,
    override val price: String,
    override val type: String,
    override var seller: String,
    override val unit: String
) : Product(name, price, "restaurant", type, seller, unit)

fun Map<String, Any>.toRestaurantProduct(): RestaurantProduct {
    return RestaurantProduct(
        this["name"].toString(),
        this["price"].toString(),
        this["type"].toString(),
        this["seller"].toString(),
        this["unit"].toString()

    )

}


