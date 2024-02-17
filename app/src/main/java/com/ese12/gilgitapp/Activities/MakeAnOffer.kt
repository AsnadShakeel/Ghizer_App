package com.ese12.gilgitapp.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.ese12.gilgitapp.Models.OfferModel
import com.ese12.gilgitapp.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.FirebaseDatabase

class MakeAnOffer : AppCompatActivity() {
    lateinit var imgUri:Uri
    lateinit var profileImage:ImageView
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_an_offer)

        var uid = intent.getStringExtra("uid")

        var close = findViewById<ImageView>(R.id.imageBack)

        close.setOnClickListener {
            finish()
        }

        var title = findViewById<EditText>(R.id.edtTitle)
        var price = findViewById<EditText>(R.id.edtPrice)
        profileImage = findViewById(R.id.profile_image)
        var description = findViewById<EditText>(R.id.edtDescription)
        var postARequest = findViewById<TextView>(R.id.postARequest)

        profileImage.setOnClickListener {
            ImagePicker.Companion.with(this).crop().start()
        }

        postARequest.setOnClickListener {
            var title = title.text.toString()
            var price = price.text.toString()
            var description = description.text.toString()

            if (title.isEmpty() || price.isEmpty() || description.isEmpty() || imgUri.toString().isEmpty()){
                Toast.makeText(this,"Filed Must Not Be Empty", Toast.LENGTH_SHORT).show()
            }else{
                var ref = FirebaseDatabase.getInstance().getReference("Offers")
                val key = ref.push().key
                val model = OfferModel(uid!!,key!!,title, price,imgUri.toString(),description)
                ref.child(uid!!).setValue(model)
                Toast.makeText(this, "Data Uploaded Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            imgUri = data!!.data!!

            profileImage.setImageURI(imgUri)

        } catch (e: Exception) {

        }
    }
}