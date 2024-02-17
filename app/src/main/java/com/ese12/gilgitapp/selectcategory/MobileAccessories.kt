package com.ese12.gilgitapp.selectcategory
//1224625694
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
import com.ese12.gilgitapp.MobileModels
import com.ese12.gilgitapp.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.FirebaseDatabase
import com.nex3z.flowlayout.FlowLayout

class MobileAccessories : AppCompatActivity() {

    private lateinit var spinner : Spinner
    private lateinit var slist:ArrayList<String>

    private lateinit var additionalInformation: TextView
    private lateinit var selectedImage: ImageView
    private lateinit var hide_linear: LinearLayout
    private var hide = true
    private var selectedManufactureItem : String = ""
    private var locationClickedWord : String = ""

    private var imgUri:Uri = Uri.parse("")

    lateinit var etTitle:EditText
    lateinit var etDescription:EditText
    lateinit var location:TextView
    lateinit var etPrice:EditText
    lateinit var etModel:EditText



    private lateinit var tvNewCondition: TextView
    private lateinit var tvUsedCondition: TextView

    private lateinit var tvNegoYes: TextView
    private lateinit var tvNegoNo: TextView

    private lateinit var tvMarchaYes: TextView
    private lateinit var tvMarchaNo: TextView


    private var newCondition: Boolean = false
    private var usedCondition: Boolean = false

    private var marchaYes: Boolean = false
    private var marchaNo: Boolean = false

    private var negoYes: Boolean = false
    private var negoNo: Boolean = false

    private lateinit var selectedConditionTextView: TextView
    private lateinit var selectedConditionText: String

    private lateinit var selectedMarchaTextView: TextView
    private lateinit var selectedMarchaText: String

    private lateinit var selectedNegoTextView: TextView
    private lateinit var selectedNegoText: String


    private lateinit var selectedWarrantyTextView: TextView
    private lateinit var selectedWarrantyText: String

    private lateinit var sevenDaysWarranty: TextView
    private lateinit var fifteenDaysWarranty: TextView
    private lateinit var thirtyDaysWarranty: TextView
    private lateinit var noWarranty: TextView

    private var sevenWarranty: Boolean = false
    private var fifteenWarranty: Boolean = false
    private var thirtyWarranty: Boolean = false
    private var noWarrantyBool: Boolean = false


    private lateinit var mobile: TextView
    private lateinit var tablet: TextView
    private lateinit var accessory: TextView

    private var mobileBool: Boolean = false
    private var tabletBool: Boolean = false
    private var accessoryBool: Boolean = false


    private lateinit var mobileTextView: TextView
    private lateinit var mobileText: String


    private lateinit var twoGB: TextView
    private lateinit var threeGB: TextView
    private lateinit var fourGB: TextView
    private lateinit var sixGB: TextView
    private lateinit var eightGB: TextView
    private lateinit var sixteenGB: TextView
    private lateinit var thirtyTwoGB: TextView
    private lateinit var sixtyFourGB: TextView

    private var twoGbBool: Boolean = false
    private var threeGbBool: Boolean = false
    private var fourGbBool: Boolean = false
    private var sixGbBool: Boolean = false
    private var eightGbBool: Boolean = false
    private var sixteenGbBool: Boolean = false
    private var thirtyTwoGbBool: Boolean = false
    private var sixtyFourGbBool: Boolean = false


    private lateinit var twoGBTextView: TextView
    private lateinit var twoGBText: String


    private lateinit var memoryEight: TextView
    private lateinit var memorySixteen: TextView
    private lateinit var memoryThirtyTwo: TextView
    private lateinit var memorySixtyFour: TextView
    private lateinit var memoryOTE: TextView
    private lateinit var memoryTFS: TextView
    private lateinit var memoryOneTb: TextView


    private lateinit var memoryEightTextView: TextView
    private lateinit var memoryEightText: String

    // Additional Boolean variables
    private var memoryEightBool: Boolean = false
    private var memorySixteenBool: Boolean = false
    private var memoryThirtyTwoBool: Boolean = false
    private var memorySixtyFourBool: Boolean = false
    private var memoryOTEBool: Boolean = false
    private var memoryTFSBool: Boolean = false
    private var memoryOneTbBool: Boolean = false


    lateinit var sellBike:TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile_accessories)
        supportActionBar?.hide()

        spinner = findViewById(R.id.spinner)
        additionalInformation = findViewById(R.id.additional)
        etTitle = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        etPrice = findViewById(R.id.etPrice)
        location = findViewById(R.id.location)
        hide_linear = findViewById(R.id.hide_linear)
        selectedImage = findViewById(R.id.selectedImage)
        etModel = findViewById(R.id.etModel)

        twoGB = findViewById(R.id.twoGB)
        threeGB = findViewById(R.id.threeGB)
        fourGB = findViewById(R.id.fourGB)
        sixGB = findViewById(R.id.sixGB)
        eightGB = findViewById(R.id.eightGB)
        sixteenGB = findViewById(R.id.sixteenGB)
        thirtyTwoGB = findViewById(R.id.thirtyTwoGB)
        sixtyFourGB = findViewById(R.id.sixtyFourGB)

        tvNewCondition = findViewById(R.id.New)
        tvUsedCondition = findViewById(R.id.used)

        mobile = findViewById(R.id.mobile)
        tablet = findViewById(R.id.tablet)
        accessory = findViewById(R.id.accessory)

        tvMarchaYes = findViewById(R.id.marchaYes)
        tvMarchaNo = findViewById(R.id.marchaNo)


        tvNegoYes = findViewById(R.id.negoYes)
        tvNegoNo = findViewById(R.id.negoNo)


        sevenDaysWarranty = findViewById(R.id.sevenDays)
        fifteenDaysWarranty = findViewById(R.id.fifteenDays)
        thirtyDaysWarranty = findViewById(R.id.thirtyDays)
        noWarranty = findViewById(R.id.noWarranty)

        // Initialize additional variables
        memoryEight = findViewById(R.id.memoryEight)
        memorySixteen = findViewById(R.id.memorySixteen)
        memoryThirtyTwo = findViewById(R.id.memoryThirtyTwo)
        memorySixtyFour = findViewById(R.id.memorySixtyFour)
        memoryOTE = findViewById(R.id.memoryOTE)
        memoryTFS = findViewById(R.id.memoryTFS)
        memoryOneTb = findViewById(R.id.memoryOneTb)


        sellBike = findViewById(R.id.sellBike)


        tvNewCondition.setOnClickListener { onTextViewClicked(it) }
        tvUsedCondition.setOnClickListener { onTextViewClicked(it) }

        mobile.setOnClickListener { onTextViewClicked5(it) }
        tablet.setOnClickListener { onTextViewClicked5(it) }
        accessory.setOnClickListener { onTextViewClicked5(it) }

        tvNegoYes.setOnClickListener { onTextViewClicked3(it) }
        tvNegoNo.setOnClickListener { onTextViewClicked3(it) }

        tvMarchaYes.setOnClickListener { onTextViewClicked4(it) }
        tvMarchaNo.setOnClickListener { onTextViewClicked4(it) }


        sevenDaysWarranty.setOnClickListener { onTextViewClicked2(it) }
        fifteenDaysWarranty.setOnClickListener { onTextViewClicked2(it) }
        thirtyDaysWarranty.setOnClickListener { onTextViewClicked2(it) }
        noWarranty.setOnClickListener { onTextViewClicked2(it) }


        twoGB.setOnClickListener { onTextViewClicked6(it) }
        threeGB.setOnClickListener { onTextViewClicked6(it) }
        fourGB.setOnClickListener { onTextViewClicked6(it) }
        sixGB.setOnClickListener { onTextViewClicked6(it) }
        eightGB.setOnClickListener { onTextViewClicked6(it) }
        sixteenGB.setOnClickListener { onTextViewClicked6(it) }
        thirtyTwoGB.setOnClickListener { onTextViewClicked6(it) }
        sixtyFourGB.setOnClickListener { onTextViewClicked6(it) }

        memoryEight.setOnClickListener { onTextViewClicked7(it) }
        memorySixteen.setOnClickListener { onTextViewClicked7(it) }
        memoryThirtyTwo.setOnClickListener { onTextViewClicked7(it) }
        memorySixtyFour.setOnClickListener { onTextViewClicked7(it) }
        memoryOTE.setOnClickListener { onTextViewClicked7(it) }
        memoryTFS.setOnClickListener { onTextViewClicked7(it) }
        memoryOneTb.setOnClickListener { onTextViewClicked7(it) }

        selectedConditionTextView = tvNewCondition
        updateSelectedTextView()

        memoryEightTextView = memoryEight
        updateSelectedTextView7()

        twoGBTextView = twoGB
        updateSelectedTextView6()

        mobileTextView = mobile
        updateSelectedTextView5()

        selectedMarchaTextView = tvMarchaYes
        updateSelectedTextView4()

        selectedWarrantyTextView = sevenDaysWarranty
        updateSelectedTextView2()

        selectedNegoTextView = tvNegoYes
        updateSelectedTextView3()


        selectedImage.setOnClickListener {
            ImagePicker.Companion.with(this).crop().start()
        }

        location.setOnClickListener{
            showBottomSheet()
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
        slist.add("Apple")
        slist.add("Xiaomi")
        slist.add("Huawei")
        slist.add("Vivo")
        slist.add("Sanmsung")
        slist.add("Nokia")
        slist.add("Realme")
        slist.add("Infinix")
        slist.add("Oppo")
        slist.add("Opne Plus")
        slist.add("Poco")
        slist.add("other")
        val adapter = ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, slist)
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

        sellBike.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("Mobiles")
            val key = ref.push().key
            val model = MobileModels(key!!, imgUri.toString(),etTitle.text.toString(),etDescription.text.toString(),selectedManufactureItem,locationClickedWord,etPrice.text.toString(),
            newCondition,usedCondition,sevenWarranty,fifteenWarranty,thirtyWarranty,noWarrantyBool,negoYes,negoNo,marchaYes,marchaNo,mobileBool,tabletBool,accessoryBool,
            twoGbBool,threeGbBool,fourGbBool,eightGbBool,sixteenGbBool,thirtyTwoGbBool,sixtyFourGbBool,memoryEightBool,memorySixteenBool,memoryThirtyTwoBool,memorySixtyFourBool,
            memoryOTEBool,memoryTFSBool,memoryOneTbBool,etModel.text.toString())
            ref.child(key!!).setValue(model)
            Toast.makeText(this, "Data Uploaded Sucessfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            imgUri = data!!.data!!

            selectedImage.setImageURI(imgUri)

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
        selectedNegoTextView = view as TextView
        updateSelectedTextView3()
    }

    private fun updateSelectedTextView3() {
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
        negoYes = selectedNegoText.equals("Yes", ignoreCase = true)
        negoNo = selectedNegoText.equals("No", ignoreCase = true)

    }


    private fun onTextViewClicked4(view: View) {
        // Set the selected TextView based on the clicked view
        selectedMarchaTextView = view as TextView
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
        marchaYes = selectedMarchaText.equals("Yes", ignoreCase = true)
        marchaNo = selectedMarchaText.equals("No", ignoreCase = true)

    }



    private fun onTextViewClicked5(view: View) {
        // Set the selected TextView based on the clicked view
        mobileTextView = view as TextView
        updateSelectedTextView5()
    }

    private fun updateSelectedTextView5() {
        // Update the background and text color of the selected TextView
        val selectedColor = getColor(R.color.black)

        mobileTextView.setBackgroundResource(R.drawable.selected_background)
        mobileTextView.setTextColor(selectedColor)

        val otherTextViews = listOf(
            mobile,
            tablet,
            accessory,
        )

        // Iterate through other TextViews to find the unselected one
        for (textView in otherTextViews) {
            if (textView != mobileTextView) {
                textView.setBackgroundResource(R.drawable.rounded_background)
                textView.setTextColor(selectedColor)
            }
        }

        // Now you can use 'selectedTextView' to get the selected text
        mobileText = mobileTextView.text.toString()

        mobileBool = mobileText.equals("Mobile", ignoreCase = true)
        tabletBool = mobileText.equals("Tablet", ignoreCase = true)
        accessoryBool = mobileText.equals("Accessory", ignoreCase = true)

        // You can store this selectedText in a variable or use it as needed
    }


    private fun onTextViewClicked6(view: View) {
        // Set the selected TextView based on the clicked view
        twoGBTextView = view as TextView
        updateSelectedTextView6()
    }

    private fun updateSelectedTextView6() {
        // Update the background and text color of the selected TextView
        val selectedColor = getColor(R.color.black)

        twoGBTextView.setBackgroundResource(R.drawable.selected_background)
        twoGBTextView.setTextColor(selectedColor)

        val otherTextViews = listOf(
            twoGB,
            threeGB,
            fourGB,
            sixGB,
            eightGB,
            sixteenGB,
            thirtyTwoGB,
            sixtyFourGB,
        )

        // Iterate through other TextViews to find the unselected one
        for (textView in otherTextViews) {
            if (textView != twoGBTextView) {
                textView.setBackgroundResource(R.drawable.rounded_background)
                textView.setTextColor(selectedColor)
            }
        }

        // Now you can use 'selectedTextView' to get the selected text
        twoGBText = twoGBTextView.text.toString()

        twoGbBool = twoGBText.equals("2GB", ignoreCase = true)
        threeGbBool = twoGBText.equals("3GB", ignoreCase = true)
        fourGbBool = twoGBText.equals("4GB", ignoreCase = true)
        sixGbBool = twoGBText.equals("6GB", ignoreCase = true)
        eightGbBool = twoGBText.equals("8GB", ignoreCase = true)
        sixteenGbBool = twoGBText.equals("16GB", ignoreCase = true)
        thirtyTwoGbBool = twoGBText.equals("32GB", ignoreCase = true)
        sixtyFourGbBool = twoGBText.equals("64GB", ignoreCase = true)

        // You can store this selectedText in a variable or use it as needed
    }



    private fun onTextViewClicked7(view: View) {
        // Set the selected TextView based on the clicked view
        memoryEightTextView = view as TextView
        updateSelectedTextView7()
    }

    private fun updateSelectedTextView7() {
        // Update the background and text color of the selected TextView
        val selectedColor = getColor(R.color.black)

        memoryEightTextView.setBackgroundResource(R.drawable.selected_background)
        memoryEightTextView.setTextColor(selectedColor)

        val otherTextViews = listOf(
            memoryEight,
            memorySixteen,
            memoryThirtyTwo,
            memorySixtyFour,
            memoryOTE,
            memoryTFS,
            memoryOneTb,
        )

        // Iterate through other TextViews to find the unselected one
        for (textView in otherTextViews) {
            if (textView != memoryEightTextView) {
                textView.setBackgroundResource(R.drawable.rounded_background)
                textView.setTextColor(selectedColor)
            }
        }

        // Now you can use 'selectedTextView' to get the selected text
        memoryEightText = memoryEightTextView.text.toString()

        memoryEightBool = memoryEightText.equals("8GB", ignoreCase = true)
        memorySixteenBool = memoryEightText.equals("16GB", ignoreCase = true)
        memoryThirtyTwoBool = memoryEightText.equals("32GB", ignoreCase = true)
        memorySixtyFourBool = memoryEightText.equals("64GB", ignoreCase = true)
        memoryOTEBool = memoryEightText.equals("128GB", ignoreCase = true)
        memoryTFSBool = memoryEightText.equals("256GB", ignoreCase = true)
        memoryOneTbBool = memoryEightText.equals("1TB", ignoreCase = true)

        // You can store this selectedText in a variable or use it as needed
    }


}