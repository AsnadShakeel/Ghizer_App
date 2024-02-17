package com.ese12.gilgitapp.selectcategory

sealed class MyResult {
    data class Success(var message : String): MyResult()
    data class Error(var message : String): MyResult()
}