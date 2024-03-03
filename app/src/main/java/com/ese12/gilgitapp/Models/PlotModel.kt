package com.ese12.gilgitapp.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlotModel(var key:String="", var plotImage:String="", var title:String="", var description:String="", var location:String="", var price:String="", var rent:Boolean=false, var sale:Boolean = false, var kanal:Boolean=false, var marla:Boolean = false, var resid:Boolean=false, var commer:Boolean=false, var agri:Boolean=false, var personName:String="", var contactNumber:String="",var stockAmount:String=""):Parcelable
