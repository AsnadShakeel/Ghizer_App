package com.ese12.gilgitapp.domain

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

class Util {
    companion object{
        fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream) // You can change the format and quality here
            return stream.toByteArray()
        }
    }

}