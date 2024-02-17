package com.ese12.gilgitapp.domain.models

import android.location.Location
import com.google.android.material.internal.ManufacturerUtils

data class RealEstateModel(
    val key: String = "", // Unique identifier for the product
    val name: String = "", // Product name
    val description: String = "", // Product description
    val price: Int = 0, // Product price
    val timeStamp: String = "", // time
    val category: String = "", // Category or type of the product
    val location: String = "", // Category or type of the product
    val city: String = "", // Category or type of the product
    val rating: Int = 0, // Average user rating
    val views: Int = 0, // Average user rating
    val inStock: Boolean = true, // Availability status
    val manufacturer: String = "",
    val warranty: String = "",
    val Negotiable: Boolean = true,
    val condition: String = "",
    val availableForMarcha: Boolean = true,
    val model: String = "",
    val imageUrl1: String = "",
    val additionalDetails: String = "",
    val imageUrl2: String = "",
    val imageUrl3: String = "",
    val imageUrl4: String = "",
    val imageUrl5: String = "",
)
