package com.ese12.gilgitapp.domain

data class FilterOptions(
    val modelName: String?,
    val minPrice: Double?,
    val maxPrice: Double?,
    val sortBy: SortByOption?
)