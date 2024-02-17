package com.ese12.gilgitapp.domain.models

data class CartModel (
    var key: String,
    val buyerUid: String,
    val timeStamp: Long, // Timestamp or date
    val totalPrice: Double,
    val quantity: Double,
    val productModel: ProductModel,
)


//     history/userUid/productModel