package com.omaraboesmail.bargain.pojo

data class IndividualProduct(
    override val name: String,
    override val price: String,
    override val unit: String,
    override var seller: String,
    val id: String = "",
    override val type: String,
    val discount: Float = 0f,
    val review: Float = 0f,
    val quantityAvailable: Int,
    var removed: Boolean = false
) : Product(name, price, "individual", type, seller, unit)

fun IndividualProduct.toHashMap(): HashMap<String, Any> {
    return hashMapOf(
        "name" to this.name,
        "price" to this.price,
        "unit" to this.unit,
        "seller" to this.seller,
        "id" to this.id,
        "type" to this.type,
        "discount" to this.discount,
        "review" to this.review,
        "quantityAvailable" to this.quantityAvailable,
        "removed" to this.removed
    )

}

fun Map<String, *>.toIndividualProduct(): IndividualProduct {
    return IndividualProduct(
        name = this["name"].toString(),
        price = this["price"].toString(),
        unit = this["unit"].toString(),
        seller = this["seller"].toString(),
        id = this["id"].toString(),
        discount = this["discount"].toString().toFloat(),
        review = this["review"].toString().toFloat(),
        removed = this["removed"].toString().toBoolean(),
        quantityAvailable = this["quantityAvailable"].toString().toInt(),
        type = this["type"].toString()

    )
}

