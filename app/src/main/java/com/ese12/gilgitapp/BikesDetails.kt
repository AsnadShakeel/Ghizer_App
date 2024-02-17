package com.ese12.gilgitapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class BikesDetails : AppCompatActivity() {
    private lateinit var img: ImageSlider
    private lateinit var shareImage: ImageView
    private lateinit var img3: ImageView
    private lateinit var tvPrice: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvWarranty: TextView
    private lateinit var tvTitle: TextView
    private lateinit var tvManufacture: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvMarcha: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvNegotiable: TextView
    private lateinit var btnChat: Button
    private lateinit var btnContact: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bikes_details)
        val receivedData = intent.getParcelableExtra<BikeModel>("list")

        val imageList = ArrayList<SlideModel>()

        img = findViewById(R.id.imageSlider)
        img3 = findViewById(R.id.img3)
        tvPrice = findViewById(R.id.tvPrice)
        tvNegotiable = findViewById(R.id.tvNegotiable)
        tvTitle = findViewById(R.id.tvTitle)
        tvCondition = findViewById(R.id.tvCondition)
        tvWarranty = findViewById(R.id.tvWarranty)
        tvMarcha = findViewById(R.id.tvMarcha)
        tvManufacture = findViewById(R.id.tvManufacture)
        tvLocation = findViewById(R.id.tvLocation)
        tvDate = findViewById(R.id.tvDate)
        tvDescription = findViewById(R.id.tvDescription)
        btnChat = findViewById(R.id.btnChat)
        btnContact = findViewById(R.id.btnContact)
        shareImage = findViewById(R.id.img2)

        imageList.add(SlideModel(receivedData!!.bikeImage,""))

        img.setImageList(imageList, ScaleTypes.CENTER_CROP)

        btnChat.setOnClickListener {
            val phoneNumber =
                "+923016826762" // Replace with the phone number you want to chat with, including the country code

            // Create a Uri for the WhatsApp chat
            val uri = "https://api.whatsapp.com/send?phone=$phoneNumber"

            // Create an Intent with the ACTION_VIEW action
            val intent = Intent(Intent.ACTION_VIEW)

            // Set the data for the intent (the URI for the WhatsApp chat)
            intent.data = Uri.parse(uri)

            // Check if WhatsApp is installed on the device
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                // WhatsApp is not installed, handle this case
                Toast.makeText(this, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show()
            }
        }

        btnContact.setOnClickListener {
            val phoneNumber = "+923016826762" // Replace with the phone number you want to call

            // Create an Intent with the ACTION_DIAL action
            val intent = Intent(Intent.ACTION_DIAL)

            // Set the data for the intent (the phone number)
            intent.data = Uri.parse("tel:$phoneNumber")

            // Check if there is an app to handle the dial action (phone app)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                // Handle the case where there is no app to make the call
                Toast.makeText(this, "No app available to make the call.", Toast.LENGTH_SHORT)
                    .show()
            }
        }



        img3.setOnClickListener {
            finish()
        }

//        tvDate.text = receivedData!!.date
        tvPrice.text = "PKR " + receivedData.price
        tvTitle.text = receivedData.title
        tvManufacture.text = receivedData.manufacture
        if (receivedData.newCondition == true) {
            tvCondition.text = "New"
        }else{
            tvCondition.text = "Used"
        }

        if (receivedData.negotiableYes == true) {
            tvNegotiable.text = "Yes"
        } else {
            tvNegotiable.text = "No"
        }


        if (receivedData.marchaYes == true) {
            tvMarcha.text = "Yes"
        } else {
            tvMarcha.text = "No"
        }

        if (receivedData.sevenDays == true) {
            tvWarranty.text = "7 Days"
        }else if (receivedData.fifteenDays==true){
            tvWarranty.text = "15 Days"
        }else if (receivedData.thirtyDays==true){
            tvWarranty.text = "30 Days"
        }else{
            tvWarranty.text = "No Warranty"
        }

        tvLocation.text = receivedData.location
        tvDescription.text = receivedData.description


        shareImage.setOnClickListener {
            // Assuming you have the URL of the image you want to share
        }
    }
}