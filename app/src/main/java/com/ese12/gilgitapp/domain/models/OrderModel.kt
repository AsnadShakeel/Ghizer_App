package com.ese12.gilgitapp.domain.models

data class OrderModel(
    val key: String,
    val orderDate: Long, // Timestamp or date
    val totalPrice: Double,
    val quantity: Double,
    val productModel: ProductModel,
    val buyerUid: String,
    val status: OrderStatus,
    val deliveryAddress: String,
    val deliveryLocation: String,
)