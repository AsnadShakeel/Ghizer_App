package com.ese12.gilgitapp

import android.location.Location
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LaptopsModel(var key:String="",var img:String="",var title:String="",var description:String="",var manufacture:String="",var location:String="",
var price:String="",var conditionNew:Boolean=false,var conditionUsed:Boolean=false,var negoYes:Boolean=false,var negoNo:Boolean=false,var marchaYes:Boolean=false,var marchaNo:Boolean=false,var productLaptop:Boolean=false,var productAccessory:Boolean=false,var model:String=""):Parcelable
