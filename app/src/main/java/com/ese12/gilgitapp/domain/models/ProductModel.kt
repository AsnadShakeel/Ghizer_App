package com.ese12.gilgitapp.domain.models

open class ProductModel(
    var key: String,
    var modelName: String,
    var title: String,
    var price: Double,
    var timeStamp: String,
    var stockAmount: Double,
    var sellerUid: String,
)
