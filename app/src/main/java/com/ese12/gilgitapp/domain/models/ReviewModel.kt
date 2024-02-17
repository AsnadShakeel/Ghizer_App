package com.ese12.gilgitapp.domain.models

data class ReviewModel(
    val key: String,
    val productKey: String,
    val userId: String,
    val userName: String, // Assuming you want to store the user's name
    val rating: Int, // Rating given by the user (e.g., out of 5 stars)
    val reviewText: String,
    val reviewDate: Long // Timestamp or date of the review
)
