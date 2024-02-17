package com.ese12.gilgitapp.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MobileModels(var key:String="",var image:String="",var title:String="",var description:String="",var manufacture:String="",
var location:String="",var price:String="",var conditionNew:Boolean=false,var conditionUsed:Boolean=false,var warrantySevenDays:Boolean=false,
var fifDaysWar:Boolean=false,var thiryDaysWar:Boolean=false,var noWarr:Boolean=false,var negoYes:Boolean=false,var negoNo:Boolean=false,var marchaYes:Boolean=false,
var marchaNo:Boolean=false,var mobile:Boolean=false,var tablet:Boolean=false,var accessory:Boolean=false,var ram2Gb:Boolean=false,var ram3Gb:Boolean=false,var ram4Gb:Boolean=false,var ram8Gb:Boolean=false,var ram16Gb:Boolean=false,var ram32Gb:Boolean=false,var ram64Gb:Boolean=false,
var memory8Gb:Boolean=false,var memory16Gb:Boolean=false,var memory32Gb:Boolean=false,var memory64Gb:Boolean=false,var memory128Gb:Boolean=false,var memory256Gb:Boolean=false,var memory1Tb:Boolean=false,var etModel:String=""):Parcelable