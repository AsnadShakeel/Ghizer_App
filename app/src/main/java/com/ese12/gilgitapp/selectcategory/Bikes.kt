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
import com.ese12.gilgitapp.Models.BikeModel
import com.ese12.gilgitapp.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.FirebaseDatabase
import com.nex3z.flowlayout.FlowLayout

class Bikes : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var additionalInformation: TextView
    private lateinit var hide_linear: LinearLayout
    private lateinit var selectImage: ImageView
    private lateinit var slist: ArrayList<String>
    private lateinit var location: TextView
    private lateinit var locationClickedWord: String
    private var hide = true
    private var imgUri: Uri = Uri.parse("")

    private var newCondition: Boolean = false
    private var usedCondition: Boolean = false

    private lateinit var sevenDaysWarranty: TextView
    private lateinit var fifteenDaysWarranty: TextView
    private lateinit var thirtyDaysWarranty: TextView
    private lateinit var noWarranty: TextView

    private var sevenWarranty: Boolean = false
    private var fifteenWarranty: Boolean = false
    private var thirtyWarranty: Boolean = false
    private var noWarrantyBool: Boolean = false

    private var yesNego: Boolean = false
    private var noNego: Boolean = false


    private var yesMarcha: Boolean = false
    private var noMarcha: Boolean = false
    private var selectedManufactureItem: String = ""


    private lateinit var tvNegotiableYes: TextView
    private lateinit var tvNegotiableNo: TextView

    private lateinit var tvMarchaYes: TextView
    private lateinit var tvMarchaNo: TextView

    private lateinit var selectedNegoTextView: TextView
    private lateinit var selectedNegoText: String

    private lateinit var selectedMarchaTextView: TextView
    private lateinit var selectedMarchaText: String

    private lateinit var selectedWarrantyTextView: TextView
    private lateinit var selectedWarrantyText: String


    private lateinit var tvNewCondition: TextView
    private lateinit var tvUsedCondition: TextView

    private lateinit var selectedConditionTextView: TextView
    private lateinit var selectedConditionText: String

    lateinit var etTitle:EditText
    lateinit var etDescription:EditText
    lateinit var etPrice:EditText
    lateinit var etModelYear:EditText
    lateinit var etEngine:EditText
    lateinit var etBikeColor:EditText
    lateinit var etRegistrationCity:EditText
    lateinit var etMilage:EditText
    lateinit var sellNowBike:TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bikes)

        supportActionBar?.hide()

        etTitle = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        etPrice = findViewById(R.id.etPrice)
        etModelYear = findViewById(R.id.etModelYear)
        etEngine = findViewById(R.id.etEngine)
        etBikeColor = findViewById(R.id.etBikeColor)
        etRegistrationCity = findViewById(R.id.etRegistrationCity)
        etMilage = findViewById(R.id.etMilage)

        sellNowBike = findViewById(R.id.sellNowBike)

        spinner = findViewById(R.id.spinner)
        additionalInformation = findViewById(R.id.additionalInformation)
        hide_linear = findViewById(R.id.hide_linear)
        location = findViewById(R.id.productLocation)
        selectImage = findViewById(R.id.selectImage)

        tvNewCondition = findViewById(R.id.New)
        tvUsedCondition = findViewById(R.id.used)

        tvNegotiableYes = findViewById(R.id.yesNego)
        tvNegotiableNo = findViewById(R.id.noNego)

        tvMarchaYes = findViewById(R.id.marchaYes)
        tvMarchaNo = findViewById(R.id.marchaNo)

        tvMarchaYes.setOnClickListener { onTextViewClicked4(it) }
        tvMarchaNo.setOnClickListener { onTextViewClicked4(it) }


        tvNewCondition.setOnClickListener { onTextViewClicked(it) }
        tvUsedCondition.setOnClickListener { onTextViewClicked(it) }


        tvNegotiableYes.setOnClickListener { onTextViewClicked3(it) }
        tvNegotiableNo.setOnClickListener { onTextViewClicked3(it) }


        sevenDaysWarranty = findViewById(R.id.sevenDays)
        fifteenDaysWarranty = findViewById(R.id.fifteenDays)
        thirtyDaysWarranty = findViewById(R.id.thirtyDays)
        noWarranty = findViewById(R.id.noWarranty)



        sevenDaysWarranty.setOnClickListener { onTextViewClicked2(it) }
        fifteenDaysWarranty.setOnClickListener { onTextViewClicked2(it) }
        thirtyDaysWarranty.setOnClickListener { onTextViewClicked2(it) }
        noWarranty.setOnClickListener { onTextViewClicked2(it) }

        selectedWarrantyTextView = sevenDaysWarranty
        updateSelectedTextView2()

        selectedMarchaTextView = tvMarchaYes
        updateSelectedTextView4()

        selectedNegoTextView = tvNegotiableYes
        updateSelectedTextView3()

        selectedConditionTextView = tvNewCondition

        updateSelectedTextView()

        location.setOnClickListener {
            showBottomSheet()
        }

        selectImage.setOnClickListener {
            ImagePicker.Companion.with(this).crop().start()
        }

        additionalInformation.setOnClickListener {
            if (hide) {
                hide_linear.visibility = View.VISIBLE
                hide = false
            } else {
                hide_linear.visibility = View.GONE
                hide = true
            }
        }


        slist = arrayListOf()
        slist.add("Yahma")
        slist.add("Honda")
        slist.add("unique")
        slist.add("united")
        slist.add("Suzuki")
        slist.add("Road prince")
        slist.add("Zxcmo")
        val adapter = ArrayAdapter(
            this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            slist
        )
        adapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                // Get the selected item from the spinner
                selectedManufactureItem = parent?.getItemAtPosition(position).toString()

                // Do something with the selected item
                // For example, you can log it or perform an action

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case where nothing is selected
            }
        }

        sellNowBike.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("Bikes")
            var key = ref.push().key
            val model = BikeModel(key!!,imgUri.toString(),etTitle.text.toString(),etDescription.text.toString(),selectedManufactureItem,locationClickedWord,etPrice.text.toString(),newCondition, usedCondition,sevenWarranty,fifteenWarranty,thirtyWarranty,noWarrantyBool,yesNego,noNego,yesMarcha,noMarcha,etModelYear.text.toString(),etEngine.text.toString(),etBikeColor.text.toString(),etRegistrationCity.text.toString(),etMilage.text.toString())
            ref.child(key!!).setValue(model)
            Toast.makeText(this, "Data Uploaded Successfully", Toast.LENGTH_SHORT).show()
            finish()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            imgUri = data!!.data!!

            selectImage.setImageURI(imgUri)

        } catch (e: Exception) {

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

    private fun onTextViewClicked(view: View) {
        // Set the selected TextView based on the clicked view
        selectedConditionTextView = (view as TextView)
        updateSelectedTextView()
    }

    private fun updateSelectedTextView() {
        // Update the background and text color of the selected TextView
        val selectedColor = getColor(R.color.black)

        selectedConditionTextView.setBackgroundResource(R.drawable.selected_background)
        selectedConditionTextView.setTextColor(selectedColor)

        val otherTextView = if (selectedConditionTextView.id == R.id.New) {
            findViewById<TextView>(R.id.used)
        } else {
            findViewById<TextView>(R.id.New)
        }

        otherTextView.setBackgroundResource(R.drawable.rounded_background)
        otherTextView.setTextColor(selectedColor)

        // Now you can use 'selectedTextView' to get the selected text
        selectedConditionText = selectedConditionTextView.text.toString()
        newCondition = selectedConditionText.equals("New", ignoreCase = true)
        usedCondition = selectedConditionText.equals("Used", ignoreCase = true)

    }


    private fun onTextViewClicked2(view: View) {
        // Set the selected TextView based on the clicked view
        selectedWarrantyTextView = view as TextView
        updateSelectedTextView2()
    }

    private fun updateSelectedTextView2() {
        // Update the background and text color of the selected TextView
        val selectedColor = getColor(R.color.black)

        selectedWarrantyTextView.setBackgroundResource(R.drawable.selected_background)
        selectedWarrantyTextView.setTextColor(selectedColor)

        val otherTextViews = listOf(
            sevenDaysWarranty,
            fifteenDaysWarranty,
            thirtyDaysWarranty,
            noWarranty
        )

        // Iterate through other TextViews to find the unselected one
        for (textView in otherTextViews) {
            if (textView != selectedWarrantyTextView) {
                textView.setBackgroundResource(R.drawable.rounded_background)
                textView.setTextColor(selectedColor)
            }
        }

        // Now you can use 'selectedTextView' to get the selected text
        selectedWarrantyText = selectedWarrantyTextView.text.toString()

        sevenWarranty = selectedWarrantyText.equals("7 Days", ignoreCase = true)
        fifteenWarranty = selectedWarrantyText.equals("15 Days", ignoreCase = true)
        thirtyWarranty = selectedWarrantyText.equals("30 Days", ignoreCase = true)
        noWarrantyBool = selectedWarrantyText.equals("No Warrenty", ignoreCase = true)

        // You can store this selectedText in a variable or use it as needed
    }


    private fun onTextViewClicked3(view: View) {
        // Set the selected TextView based on the clicked view
        selectedNegoTextView = (view as TextView)
        updateSelectedTextView3()
    }

    private fun updateSelectedTextView3() {
        // Update the background and text color of the selected TextView
        val selectedColor = getColor(R.color.black)

        selectedNegoTextView.setBackgroundResource(R.drawable.selected_background)
        selectedNegoTextView.setTextColor(selectedColor)

        val otherTextView = if (selectedNegoTextView.id == R.id.yesNego) {
            findViewById<TextView>(R.id.noNego)
        } else {
            findViewById<TextView>(R.id.yesNego)
        }

        otherTextView.setBackgroundResource(R.drawable.rounded_background)
        otherTextView.setTextColor(selectedColor)

        // Now you can use 'selectedTextView' to get the selected text
        selectedNegoText = selectedNegoTextView.text.toString()

        yesNego = selectedNegoText.equals("Yes", ignoreCase = true)
        noNego = selectedNegoText.equals("No", ignoreCase = true)

        // You can store this selectedText in a variable or use it as needed
    }


    private fun onTextViewClicked4(view: View) {
        // Set the selected TextView based on the clicked view
        selectedMarchaTextView = (view as TextView)
        updateSelectedTextView4()
    }


    private fun updateSelectedTextView4() {
        // Update the background and text color of the selected TextView
        val selectedColor = getColor(R.color.black)

        selectedMarchaTextView.setBackgroundResource(R.drawable.selected_background)
        selectedMarchaTextView.setTextColor(selectedColor)

        val otherTextView = if (selectedMarchaTextView.id == R.id.marchaYes) {
            findViewById<TextView>(R.id.marchaNo)
        } else {
            findViewById<TextView>(R.id.marchaYes)
        }

        otherTextView.setBackgroundResource(R.drawable.rounded_background)
        otherTextView.setTextColor(selectedColor)

        // Now you can use 'selectedTextView' to get the selected text
        selectedMarchaText = selectedMarchaTextView.text.toString()

        yesMarcha = selectedMarchaText.equals("Yes", ignoreCase = true)
        noMarcha = selectedMarchaText.equals("No", ignoreCase = true)

        // You can store this selectedText in a variable or use it as needed
    }

}