package com.ese12.gilgitapp.selectcategory

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import com.ese12.gilgitapp.HouseModel
import com.ese12.gilgitapp.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.FirebaseDatabase
import com.nex3z.flowlayout.FlowLayout

class House : AppCompatActivity() {
    lateinit var selectedImage:ImageView
    var imageUri:Uri = Uri.parse("")

    lateinit var etTitle:EditText
    lateinit var etDescription:EditText
    lateinit var etPrice:EditText
    lateinit var personName:EditText
    lateinit var contactNumber:EditText
    lateinit var securityDeposit:EditText
    lateinit var location:TextView
    var locationClickedWord:String = ""

    private var forSale: Boolean = false
    private var forRent: Boolean = false

    private var yesNego: Boolean = false
    private var noNego: Boolean = false


    private lateinit var tvNegotiableYes: TextView
    private lateinit var tvNegotiableNo: TextView


    private lateinit var selectedNegoTextView: TextView
    private lateinit var selectedNegoText: String

    private lateinit var tvForSale: TextView
    private lateinit var tvForRent: TextView

    private lateinit var additionalInformation: TextView
    private lateinit var linear: LinearLayout
    private var hide: Boolean = true

    private lateinit var sellHouse: TextView

    private lateinit var selectedForSaleTextView: TextView
    private lateinit var selectedForSaleText: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house)
        selectedImage = findViewById(R.id.selectedImage)

        etTitle = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        etPrice = findViewById(R.id.etPrice)
        location = findViewById(R.id.location)

        tvForSale = findViewById(R.id.forSale)
        tvForRent = findViewById(R.id.forRent)

        personName = findViewById(R.id.personName)
        contactNumber = findViewById(R.id.contactNumber)

        additionalInformation = findViewById(R.id.additionalInformation)
        linear = findViewById(R.id.linear)

        securityDeposit = findViewById(R.id.securityDeposit)

        sellHouse = findViewById(R.id.sellHouse)

        tvNegotiableYes = findViewById(R.id.negoYes)
        tvNegotiableNo = findViewById(R.id.negoNo)

        tvForSale.setOnClickListener { onTextViewClicked(it) }
        tvForRent.setOnClickListener { onTextViewClicked(it) }

        tvNegotiableYes.setOnClickListener { onTextViewClicked1(it) }
        tvNegotiableNo.setOnClickListener { onTextViewClicked1(it) }

        selectedForSaleTextView = tvForSale
        updateSelectedTextView()

        additionalInformation.setOnClickListener {
            if (hide) {
                linear.visibility = View.VISIBLE
                hide = false
            } else {
                linear.visibility = View.GONE
                hide = true
            }
        }

        selectedNegoTextView = tvNegotiableYes
        updateSelectedTextView1()

        location.setOnClickListener {
            showBottomSheet()
        }

        selectedImage.setOnClickListener {
            ImagePicker.Companion.with(this).crop().start()
        }

        sellHouse.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("House")
            val key = ref.push().key
            val model = HouseModel(key!!, imageUri.toString(),etTitle.text.toString(),etDescription.text.toString(),etPrice.text.toString(),locationClickedWord,forSale, forRent,securityDeposit.text.toString(),yesNego,noNego,personName.text.toString(),contactNumber.text.toString())
            ref.child(key!!).setValue(model)
            Toast.makeText(this, "Data Uploaded Successfully", Toast.LENGTH_SHORT).show()
            finish()
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
            imageUri = data!!.data!!

            selectedImage.setImageURI(imageUri)

        } catch (e: Exception) {

        }
    }


    private fun onTextViewClicked(view: View) {
        // Set the selected TextView based on the clicked view
        selectedForSaleTextView = (view as TextView)
        updateSelectedTextView()
    }

    private fun updateSelectedTextView() {
        // Update the background and text color of the selected TextView
        val selectedColor = getColor(R.color.black)

        selectedForSaleTextView.setBackgroundResource(R.drawable.selected_background)
        selectedForSaleTextView.setTextColor(selectedColor)

        val otherTextView = if (selectedForSaleTextView.id == R.id.forSale) {
            findViewById<TextView>(R.id.forRent)
        } else {
            findViewById<TextView>(R.id.forSale)
        }

        otherTextView.setBackgroundResource(R.drawable.rounded_background)
        otherTextView.setTextColor(selectedColor)

        // Now you can use 'selectedTextView' to get the selected text
        selectedForSaleText = selectedForSaleTextView.text.toString()
        forSale = selectedForSaleText.equals("For Sale", ignoreCase = true)
        forRent = selectedForSaleText.equals("For Rent", ignoreCase = true)

    }

    private fun onTextViewClicked1(view: View) {
        // Set the selected TextView based on the clicked view
        selectedNegoTextView = (view as TextView)
        updateSelectedTextView1()
    }

    private fun updateSelectedTextView1() {
        // Update the background and text color of the selected TextView
        val selectedColor = getColor(R.color.black)

        selectedNegoTextView.setBackgroundResource(R.drawable.selected_background)
        selectedNegoTextView.setTextColor(selectedColor)

        val otherTextView = if (selectedNegoTextView.id == R.id.negoYes) {
            findViewById<TextView>(R.id.negoNo)
        } else {
            findViewById<TextView>(R.id.negoYes)
        }

        otherTextView.setBackgroundResource(R.drawable.rounded_background)
        otherTextView.setTextColor(selectedColor)

        // Now you can use 'selectedTextView' to get the selected text
        selectedNegoText = selectedNegoTextView.text.toString()
        yesNego = selectedNegoText.equals("Yes", ignoreCase = true)
        noNego = selectedNegoText.equals("No", ignoreCase = true)

    }

}
