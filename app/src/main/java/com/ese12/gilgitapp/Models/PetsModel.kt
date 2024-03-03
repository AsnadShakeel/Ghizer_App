package com.ese12.gilgitapp.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PetsModel(var key:String="",var imageUri:String="", var title:String="", var description:String="", var price:String="", var location:String="",
                     var male:Boolean=false, var female:Boolean=false, var negoYes:Boolean=false, var negoNo:Boolean=false, var petBreed:String="", var petAge:String="",var stockAmount:String=""):Parcelable
