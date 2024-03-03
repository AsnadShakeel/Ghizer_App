package com.ese12.gilgitapp.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OfficeModel(var key:String="",var image:String="", var title:String="", var description:String="", var price:String="", var location:String="",
                       var forSale:Boolean=false, var forRent:Boolean=false, var secuirtyDeposit:String="", var negoYes:Boolean=false, var negoNo:Boolean=false,var numberOfRoom:String="", var personName:String="", var contactNumber:String="",var stockAmount:String=""):Parcelable
