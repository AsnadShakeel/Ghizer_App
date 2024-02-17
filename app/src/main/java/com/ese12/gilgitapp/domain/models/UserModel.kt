package com.ese12.gilgitapp.domain.models

data class UserModel(
    val userUid: String,
    var userName: String,
    var email: String,
    var profileImage: String?,
    var phone: String?,
    var city: String?,
    var location: String
)