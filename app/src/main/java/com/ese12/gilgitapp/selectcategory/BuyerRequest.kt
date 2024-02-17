package com.ese12.gilgitapp.selectcategory

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import com.ese12.gilgitapp.BuyerRequestsAdapter
import com.ese12.gilgitapp.MainActivity
import com.ese12.gilgitapp.R
import com.ese12.gilgitapp.domain.models.BuyerRequestModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nex3z.flowlayout.FlowLayout
import de.hdodenhof.circleimageview.CircleImageView

import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class BuyerRequest : AppCompatActivity(){

    private lateinit var spinner: Spinner
    private lateinit var slist: ArrayList<String>
    private lateinit var location: TextView
    private lateinit var edtLooking: EditText
    private lateinit var edtPrice: EditText
    private lateinit var edtUserName: EditText
    private lateinit var postARequest: TextView
    private lateinit var imageBack: ImageView
    private lateinit var profileImage: CircleImageView
    private lateinit var categorySelectedItem: String
    private lateinit var locationClickedWord: String
    private lateinit var buyerRequestModel: BuyerRequestModel
    private lateinit var imgUri: Uri

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer_request)
        supportActionBar?.hide()

        buyerRequestModel = BuyerRequestModel()
        spinner = findViewById(R.id.spinner)
        postARequest = findViewById(R.id.postARequest)
        location = findViewById(R.id.location)
        edtLooking = findViewById(R.id.edtLooking)
        edtPrice = findViewById(R.id.etdPrice)
        profileImage = findViewById(R.id.profile_image)
        imageBack = findViewById(R.id.imageBack)
        edtUserName = findViewById(R.id.edtUserName)
        slist = arrayListOf()

        slist.add("Cars")
        slist.add("Bikes")
        slist.add("Laptops")
        slist.add("Mobiles")
        slist.add("Appliances")
        slist.add("Plot")
        slist.add("Home")
        slist.add("Shops")
        slist.add("Offices")
        slist.add("Furniture")
        slist.add("Pets")
        slist.add("Fashion")
        slist.add("Books")
        slist.add("Dry Fruits")
        slist.add("other")

        imageBack.setOnClickListener {
            finish()
        }

        val adapter = ArrayAdapter(
            this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            slist
        )
        adapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                // Get the selected item from the spinner
                categorySelectedItem = slist[position]

                Log.i("TAG", "onItemSelected: item from category $categorySelectedItem")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing here if you don't need to handle the case where nothing is selected
            }
        }

        location.setOnClickListener {
            showBottomSheet()
        }

        profileImage.setOnClickListener {
            ImagePicker.Companion.with(this)
                .crop()
                .start()
        }

        postARequest.setOnClickListener {
            var lookingFor = edtLooking.text.toString()
            var price = edtPrice.text.toString()
            var userName = edtUserName.text.toString()

            if (lookingFor.isEmpty()||price.isEmpty()||userName.isEmpty()||locationClickedWord.isEmpty()||categorySelectedItem.isEmpty()){
                Toast.makeText(this, "Filed Must Not Be Empty", Toast.LENGTH_SHORT).show()
            }else {

                val ref = FirebaseDatabase.getInstance().getReference("Buyer Requests")
                var key = ref.push().key
                val uid = FirebaseAuth.getInstance().currentUser!!.uid
                val model = BuyerRequestModel(
                    uid,
                    userName,
                    imgUri.toString(),
                    lookingFor,
                    categorySelectedItem,
                    price.toString().toInt(),
                    locationClickedWord
                )
                ref.child(uid).child(key!!).setValue(model)
                startActivity(Intent(this,MainActivity::class.java))

                Toast.makeText(this, "Data Added Successfully", Toast.LENGTH_SHORT).show()
            }
            // locationClickedWord
            // categorySelectedItem
        }


    }

    private fun showBottomSheet() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottomsheetforlocation)
        var autoCompleteTextView =
            dialog.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView1)
        val flow = dialog.findViewById<FlowLayout>(R.id.flow)

        var text =
            "Karachi,Lahore,Islamabad,Faisalabad,Rawalpindi,Multan,Peshawar,Quetta,Sialkot,Gujranwala,Hyderabad,Abbottabad,Bahawalpur,Sargodha,Sahiwal,Jhang,Okara,Gujrat,Mirpur,Sheikhupura,Sialkot,Rahim Yar Khan,Marian,Sukkur,Larkana,Nawabshah,Mingora,Dera Ghazi Khan,Bhimber,Chiniot"
        val words = text.split(",").toTypedArray()

        var countries = arrayOf(
            "Pakistan",
            "India",
            "United States",
            "United Kingdom",
            "Canada",
            "Australia"
        )

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1, countries)
        autoCompleteTextView.setAdapter(adapter1)


        for (i in words.indices) {
            val textView = TextView(this)
            textView.text = words[i].trim()
            textView.textSize = 15f
            textView.setBackgroundResource(R.drawable.rounded_background)
            textView.minWidth = 170
            textView.gravity = Gravity.CENTER_HORIZONTAL
            visi(textView)
            textView.setPadding(20, 10, 20, 15)
            flow.setPadding(16, 25, 16, 25)
            textView.setTextColor(resources.getColor(R.color.black))
            val customFont = ResourcesCompat.getFont(this, R.font.nunitolight)



            textView.setTypeface(customFont)
            flow.addView(textView)

            for (j in 0 until flow.childCount) {
                val innerTextView = flow.getChildAt(j) as TextView
                var isSelected = false
                innerTextView.setOnClickListener {
                    locationClickedWord = innerTextView.text.toString()
                    location.text = locationClickedWord
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setGravity(Gravity.BOTTOM)
    }

    private fun visi(textView: TextView) {
        if (textView.text.toString().isEmpty()) {
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
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



// ali hamza uid = h7PNQnOGJxSVyXLzZGPWB8468Ov1
