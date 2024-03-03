package com.ese12.gilgitapp.Models

data class BooksModel(var key:String="", var imageUri:String="", var title:String="", var description:String="", var price:String="", var location:String="",
                      var new:Boolean=false, var used:Boolean=false, var negoYes:Boolean=false, var negoNo:Boolean=false,var stockAmount:String="")
