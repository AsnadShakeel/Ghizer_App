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
import com.ese12.gilgitapp.Models.CarsSuvsModel
import com.ese12.gilgitapp.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.FirebaseDatabase
import com.nex3z.flowlayout.FlowLayout

class CarsSuvs : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var slist: ArrayList<String>
    private lateinit var additional: TextView
    private lateinit var productTitle: EditText
    private lateinit var productDescription: EditText
    private lateinit var edtMilage: EditText
    private lateinit var edtRegistrationCity: EditText
    private lateinit var productPrice: EditText
    private lateinit var edtEngine: EditText
    private lateinit var location: TextView
    private lateinit var hide_linear: LinearLayout
    private lateinit var carImage: ImageView
    private lateinit var selectedManfactureItem: String
    private lateinit var locationClickedWord: String
    private lateinit var selectedConditionText: String
    private lateinit var selectedWarrantyText: String
    private lateinit var selectedNegoText: String
    private lateinit var selectedMarchaText: String
    private lateinit var selectedPetrolText: String
    private var hide = true
    private lateinit var imgUri: Uri
    private lateinit var tvNewCondition: TextView
    private lateinit var tvUsedCondition: TextView
    private lateinit var sevenDaysWarranty: TextView
    private lateinit var fifteenDaysWarranty: TextView
    private lateinit var thirtyDaysWarranty: TextView
    private lateinit var noWarranty: TextView

    private var newCondition:Boolean=false
    private var usedCondition:Boolean=false
    private var yesNego:Boolean=false
    private var noNego:Boolean=false
    private var yesMarcha:Boolean=false
    private var noMarcha:Boolean=false
    private var petrol:Boolean=false
    private var diesel:Boolean=false
    private var hybrid:Boolean=false
    private var cng:Boolean=false
    private var sevenWarranty:Boolean=false
    private var fifteenWarranty:Boolean=false
    private var thirtyWarranty:Boolean=false
    private var noWarrantyBool:Boolean=false

    private lateinit var tvNegotiableYes: TextView
    private lateinit var tvNegotiableNo: TextView

    private lateinit var marchaYes: TextView
    private lateinit var marchaNo: TextView

    private lateinit var tvPetrol: TextView
    private lateinit var tvDiesel: TextView
    private lateinit var tvHybrid: TextView
    private lateinit var tvCng: TextView

    private lateinit var selectedConditionTextView: TextView
    private lateinit var selectedWarrantyTextView: TextView
    private lateinit var selectedNegoTextView: TextView
    private lateinit var selectedMarchaTextView: TextView
    private lateinit var selectedPetrolTextView: TextView


    private lateinit var airBagBox: CheckBox
    private lateinit var powerBag: CheckBox
    private lateinit var absBag: CheckBox
    private lateinit var conditioningBag: CheckBox
    private lateinit var mirrorBags: CheckBox
    private lateinit var usbBags: CheckBox

    private var airBoxBo : Boolean = false
    private var powerBagBo : Boolean = false
    private var absBagBo : Boolean = false
    private var coBagBo : Boolean = false
    private var mirBagBo : Boolean = false
    private var usbBagBo : Boolean = false

    private lateinit var tvSellNow: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cars_suvs)
        supportActionBar?.hide()

        spinner = findViewById(R.id.spinner)
        additional = findViewById(R.id.additional)
        hide_linear = findViewById(R.id.hide_linear)
        carImage = findViewById(R.id.carImage)
        productTitle = findViewById(R.id.productTitle)
        edtEngine = findViewById(R.id.edtEngine)
        edtRegistrationCity = findViewById(R.id.edtRegistrationCity)
        edtMilage = findViewById(R.id.edtMilage)
        productDescription = findViewById(R.id.productDescription)
        location = findViewById(R.id.productLocation)
        tvNewCondition = findViewById(R.id.New)
        productPrice = findViewById(R.id.productPrice)
        tvUsedCondition = findViewById(R.id.used)

        tvSellNow = findViewById(R.id.tvSellNow)

        sevenDaysWarranty = findViewById(R.id.sevenDays)
        fifteenDaysWarranty = findViewById(R.id.fifteenDays)
        thirtyDaysWarranty = findViewById(R.id.thirtyDays)
        noWarranty = findViewById(R.id.noWarrenty)


        airBagBox = findViewById(R.id.airBagBox)
        powerBag = findViewById(R.id.powerBag)
        absBag = findViewById(R.id.absBag)
        conditioningBag = findViewById(R.id.conditioningBag)
        mirrorBags = findViewById(R.id.mirrorBags)
        usbBags = findViewById(R.id.usbBags)


        tvPetrol = findViewById(R.id.tvPetrol)
        tvDiesel = findViewById(R.id.tvDiesel)
        tvHybrid = findViewById(R.id.tvHybrid)
        tvCng = findViewById(R.id.tvCNG)

        tvNegotiableYes = findViewById(R.id.tvNegotiableYes)
        tvNegotiableNo = findViewById(R.id.tvNegotiableNo)

        tvNewCondition.setOnClickListener { onTextViewClicked(it) }
        tvUsedCondition.setOnClickListener { onTextViewClicked(it) }


        marchaYes = findViewById(R.id.marchaYes)
        marchaNo = findViewById(R.id.marchaNo)

        marchaYes.setOnClickListener { onTextViewClicked4(it) }
        marchaNo.setOnClickListener { onTextViewClicked4(it) }


        tvNegotiableYes.setOnClickListener { onTextViewClicked3(it) }
        tvNegotiableNo.setOnClickListener { onTextViewClicked3(it) }

        sevenDaysWarranty.setOnClickListener { onTextViewClicked2(it) }
        fifteenDaysWarranty.setOnClickListener { onTextViewClicked2(it) }
        thirtyDaysWarranty.setOnClickListener { onTextViewClicked2(it) }
        noWarranty.setOnClickListener { onTextViewClicked2(it) }

        tvPetrol.setOnClickListener { onTextViewClicked5(it) }
        tvDiesel.setOnClickListener { onTextViewClicked5(it) }
        tvHybrid.setOnClickListener { onTextViewClicked5(it) }
        tvCng.setOnClickListener { onTextViewClicked5(it) }

        // Initialize the selected TextView (default to 'sevenDaysWarranty')
        selectedNegoTextView = tvNegotiableYes
        updateSelectedTextView3()
        // Initialize the selected TextView (default to 'sevenDaysWarranty')
        selectedPetrolTextView = tvPetrol
        updateSelectedTextView5()

        // Initialize the selected TextView (default to 'sevenDaysWarranty')
        selectedMarchaTextView = marchaYes
        updateSelectedTextView4()

        // Initialize the selected TextView (default to 'sevenDaysWarranty')
        selectedWarrantyTextView = sevenDaysWarranty
        updateSelectedTextView2()

        selectedConditionTextView = tvNewCondition

        updateSelectedTextView()

        slist = arrayListOf()

        slist.add("Toyota")
        slist.add("Honda")
        slist.add("Nissan")
        slist.add("Hyundai")
        slist.add("Suzuki")
        slist.add("Jeep")
        slist.add("Mazda")
        slist.add("Civic")
        slist.add("Ford")
        slist.add("Mitsubishi")
        slist.add("Audi")
        slist.add("other")

        location.setOnClickListener {
            showBottomSheet()
        }

        carImage.setOnClickListener {
            ImagePicker.Companion.with(this).crop().start()
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
                selectedManfactureItem = slist[position]

                Log.i("TAG", "onItemSelected: item from category $selectedManfactureItem")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing here if you don't need to handle the case where nothing is selected
            }
        }

        additional.setOnClickListener {
            if (hide) {
                hide_linear.visibility = View.VISIBLE
                hide = false
            } else {
                hide_linear.visibility = View.GONE
                hide = true
            }
        }

        airBagBox.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked){
                airBoxBo = true
            }else{
                airBoxBo = false
            }
        }

        powerBag.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked){
                powerBagBo = true
            }else{
                powerBagBo = false
            }
        }

        mirrorBags.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked){
                mirBagBo = true
            }else{
                mirBagBo = false
            }
        }

        conditioningBag.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked){
                coBagBo = true
            }else{
                coBagBo = false
            }
        }
        absBag.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked){
                absBagBo = true
            }else{
                absBagBo = false
            }
        }
        usbBags.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked){
                usbBagBo = true
            }else{
                usbBagBo = false
            }
        }

        tvSellNow.setOnClickListener {

            var ref = FirebaseDatabase.getInstance().getReference("Cars")
            var key = ref.push().key
            var model = CarsSuvsModel(imgUri.toString(),productTitle.text.toString(),productDescription.text.toString(),selectedManfactureItem,locationClickedWord,
            productPrice.text.toString(),newCondition,usedCondition,sevenWarranty,fifteenWarranty,thirtyWarranty,noWarrantyBool,yesNego,noNego,yesMarcha,
            noMarcha,edtEngine.text.toString(),edtRegistrationCity.text.toString(),petrol, diesel, hybrid, cng,edtMilage.text.toString(),airBoxBo,coBagBo,
            powerBagBo,mirBagBo,absBagBo,usbBagBo)
            ref.child(key!!).setValue(model)
            Toast.makeText(this, "Data Uploaded Successfull", Toast.LENGTH_SHORT).show()
            finish()
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

        val otherTextView = if (selectedNegoTextView.id == R.id.tvNegotiableYes) {
            findViewById<TextView>(R.id.tvNegotiableNo)
        } else {
            findViewById<TextView>(R.id.tvNegotiableYes)
        }

        otherTextView.setBackgroundResource(R.drawable.rounded_background)
        otherTextView.setTextColor(selectedColor)

        // Now you can use 'selectedTextView' to get the selected text
        selectedNegoText = selectedNegoTextView.text.toString()

        yesNego = selectedNegoText.equals("Yes", ignoreCase = true)
        noNego = selectedNegoText.equals("No", ignoreCase = true)

        // You can store this selectedText in a variable or use it as needed
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

    private fun onTextViewClicked5(view: View) {
        // Set the selected TextView based on the clicked view
        selectedPetrolTextView = view as TextView
        updateSelectedTextView5()
    }

    private fun updateSelectedTextView5() {
        // Update the background and text color of the selected TextView
        val selectedColor = getColor(R.color.black)

        selectedPetrolTextView.setBackgroundResource(R.drawable.selected_background)
        selectedPetrolTextView.setTextColor(selectedColor)

        val otherTextViews = listOf(
            tvPetrol,
            tvDiesel,
            tvHybrid,
            tvCng
        )

        // Iterate through other TextViews to find the unselected one
        for (textView in otherTextViews) {
            if (textView != selectedPetrolTextView) {
                textView.setBackgroundResource(R.drawable.rounded_background)
                textView.setTextColor(selectedColor)
            }
        }

        // Now you can use 'selectedTextView' to get the selected text
        selectedPetrolText = selectedPetrolTextView.text.toString()

        petrol = selectedPetrolText.equals("Petrol", ignoreCase = true)
        diesel = selectedPetrolText.equals("Diesel", ignoreCase = true)
        hybrid = selectedPetrolText.equals("Hybrid", ignoreCase = true)
        cng = selectedPetrolText.equals("CNG", ignoreCase = true)

        // You can store this selectedText in a variable or use it as needed
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            imgUri = data!!.data!!

            carImage.setImageURI(imgUri)

        } catch (e: Exception) {

        }
    }
}