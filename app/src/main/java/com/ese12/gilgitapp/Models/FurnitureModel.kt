package com.ese12.gilgitapp.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FurnitureModel(var key:String="", var image:String="", var title:String="", var manufacture:String="", var description:String="", var location:String="", var price:String="", var new:Boolean=false, var used:Boolean = false, var negoYes:Boolean=false, var negoNo:Boolean=false, var itemColor:String="",var stockAmount:String=""):Parcelable
