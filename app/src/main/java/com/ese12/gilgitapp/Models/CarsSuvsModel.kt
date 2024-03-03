package com.ese12.gilgitapp.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CarsSuvsModel(var carImage:String="",var title:String="",var description:String="",var manufacturer:String="",
var location:String="",var price:String="",var new:Boolean = false,var used:Boolean = false,var sevenDaysWarranty:Boolean=false,var fifteenDaysWarranty:Boolean=false,
var thirtyDaysWarranty:Boolean=false,var noWarranty:Boolean=false,var negotiableYes:Boolean = false,var negotiableNo:Boolean = false,var availableForMarchaYes:Boolean=false,
var availableForMarchaNo:Boolean=false,var engine:String="",var registrationCity:String="",var petrol:Boolean=false,var diesel:Boolean=false,var hybrid:Boolean=false,
var cng:Boolean=false,var millage:String="",var airBags:Boolean=false,var airConditioning:Boolean=false,var power:Boolean=false,var mirror:Boolean = false,var abs:Boolean=false,var usb:Boolean=false,var stockAmount:String=""):Parcelable