package com.ese12.gilgitapp.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BikeModel(var key:String="",var bikeImage:String="",var title:String="",var description:String="",var manufacture:String="",var location:String="",var price:String="",var newCondition:Boolean=false,var usedCondition:Boolean = false, var sevenDays:Boolean=false,var fifteenDays:Boolean = false,var thirtyDays:Boolean=false,var noWarranty:Boolean=false,var negotiableYes:Boolean=false,var negotiableNo:Boolean=false,var marchaYes:Boolean=false,var marchaNo:Boolean=false,var modelYear:String="",var engine:String="",var bikeColor:String="",var registrationCity:String="",var milage:String=""):Parcelable
