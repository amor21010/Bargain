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
    var trustPoints: Int
)

fun Map<String, Any>.toUserObject(): User {
    return User(
        id = this.get("id").toString(),
        email = this.get("email").toString(),
        name = this.get("name").toString(),
        phone = this.get("phone").toString(),
        password = this.get("password").toString(),
        address = this.get("address").toString(),
        photoUrl = this.get("photoUrl").toString(),
        approved = this.get("approved").toString().toBoolean(),
        nationalId = this.get("nationalId").toString(),
        trustPoints = this.get("trustPoints").toString().toInt()
    )


}

fun User.ToMap(): HashMap<String, Any> {

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
        "trustPoints" to this.trustPoints
    )

}