package com.ese12.gilgitapp.domain

sealed class MyResult {
    data class Success(val message: String) : MyResult()
    data class Error(val message: String) : MyResult()
}
