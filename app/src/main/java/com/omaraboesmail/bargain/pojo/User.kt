package com.omaraboesmail.bargain.pojo

data class User(
    var id: String,
    var name: String,
    var email: String,
    var phone: String,
    var password: String,
    var address: String,
    var photoUrl: String,
    var nationalId: String,
    var approved: Boolean,
    var trustPoints: Int,
    var isAdmin: Boolean,
    var isSeller: Boolean

)

fun Map<String, Any>.toUserObject(): User {
    return User(
        id = this["id"].toString(),
        email = this["email"].toString(),
        name = this["name"].toString(),
        phone = this["phone"].toString(),
        password = this["password"].toString(),
        address = this["address"].toString(),
        photoUrl = this["photoUrl"].toString(),
        approved = this["approved"].toString().toBoolean(),
        nationalId = this["nationalId"].toString(),
        trustPoints = this["trustPoints"].toString().toInt(),
        isAdmin = this["isAdmin"].toString().toBoolean(),
        isSeller = this["isSeller"].toString().toBoolean()
    )
}

fun User.toHashMap(): HashMap<String, Any> {

    return hashMapOf(
        "id" to this.id,
        "email" to this.email,
        "name" to this.name,
        "phone" to this.phone,
        "password" to this.password,
        "address" to this.address,
        "nationalId" to this.nationalId,
        "photoUrl" to this.photoUrl,
        "approved" to this.approved,
        "trustPoints" to this.trustPoints,
        "isAdmin" to this.isAdmin,
        "isSeller" to this.isSeller

    )

}