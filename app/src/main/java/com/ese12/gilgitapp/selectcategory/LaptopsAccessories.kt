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
import com.ese12.gilgitapp.Models.LaptopsModel
import com.ese12.gilgitapp.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.FirebaseDatabase
import com.nex3z.flowlayout.FlowLayout

class LaptopsAccessories : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var selectImage: ImageView
    private lateinit var location: TextView
    private lateinit var etModel: EditText
    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var etPrice: EditText
    private lateinit var additionalInformation: TextView
    private lateinit var hide_leaner: LinearLayout
    private var hide: Boolean = true
    private lateinit var slist: ArrayList<String>
    private var locationClickedWord: String = ""
    private var selectedManufactureItem: String = ""
    private var imgUri: Uri = Uri.parse("")

    private var newCondition: Boolean = false
    private var usedCondition: Boolean = false

    private lateinit var tvNewCondition: TextView
    private lateinit var tvUsedCondition: TextView

    private lateinit var selectedConditionTextView: TextView
    private lateinit var selectedConditionText: String

    private var negoYes: Boolean = false
    private var negoNO: Boolean = false

    private lateinit var tvNegoYes: TextView
    private lateinit var tvNegoNo: TextView

    private lateinit var sellLaptop: TextView

    private lateinit var selectedNegoTextView: TextView
    private lateinit var selectedNegoText: String

    private var marchaYes: Boolean = false
    private var marchaNo: Boolean = false

    private lateinit var tvMarchaYes: TextView
    private lateinit var tvMarchaNo: TextView

    private lateinit var selectedMarchaTextView: TextView
    private lateinit var selectedMarchaText: String

    private var productLaptop: Boolean = false
    private var productAccessory: Boolean = false

    private lateinit var tvProductLaptop: TextView
    private lateinit var tvProductAccessory: TextView

    private lateinit var selectedProductTextView: TextView
    private lateinit var selectedProductText: String


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laptops_accessories)
        supportActionBar?.hide()

        spinner = findViewById(R.id.spinner)
        additionalInformation = findViewById(R.id.additionalInformation)
        selectImage = findViewById(R.id.selectImage)
        location = findViewById(R.id.location)
        hide_leaner = findViewById(R.id.hide_linear)
        sellLaptop = findViewById(R.id.sellLaptop)

        tvNewCondition = findViewById(R.id.New)
        tvUsedCondition = findViewById(R.id.used)

        etModel = findViewById(R.id.etModel)
        etTitle = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        etPrice = findViewById(R.id.etPrice)

        tvNewCondition.setOnClickListener { onTextViewClicked(it) }
        tvUsedCondition.setOnClickListener { onTextViewClicked(it) }

        selectedConditionTextView = tvNewCondition
        updateSelectedTextView()

        tvMarchaYes = findViewById(R.id.marchaYes)
        tvMarchaNo = findViewById(R.id.marchaNo)

        tvMarchaYes.setOnClickListener { onTextViewClicked2(it) }
        tvMarchaNo.setOnClickListener { onTextViewClicked2(it) }

        selectedMarchaTextView = tvMarchaYes
        updateSelectedTextView2()

        tvNegoYes = findViewById(R.id.negoYes)
        tvNegoNo = findViewById(R.id.negoNo)

        tvNegoYes.setOnClickListener { onTextViewClicked1(it) }
        tvNegoNo.setOnClickListener { onTextViewClicked1(it) }

        tvProductLaptop = findViewById(R.id.laptopProduct)
        tvProductAccessory = findViewById(R.id.accessoryProduct)

        tvProductLaptop.setOnClickListener { onTextViewClicked3(it) }
        tvProductAccessory.setOnClickListener { onTextViewClicked3(it) }

        selectedConditionTextView = tvNewCondition
        updateSelectedTextView()

        selectedNegoTextView = tvNegoYes
        updateSelectedTextView1()

        selectedMarchaTextView = tvMarchaYes
        updateSelectedTextView2()

        selectedProductTextView = tvProductLaptop
        updateSelectedTextView3()

        slist = arrayListOf()
        slist.add("Macbook")
        slist.add("Dell")
        slist.add("HP")
        slist.add("Asus")
        slist.add("Acer")
        slist.add("Lenovo")
        slist.add("Samsung")
        slist.add("Toshiba")
        slist.add("LG")
        slist.add("Sony")
        slist.add("Panasonic")
        slist.add("Razer")
        slist.add("other")
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
                selectedManufactureItem = parent?.getItemAtPosition(position).toString()

                // Do something with the selected item
                // For example, you can log it or perform an action

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case where nothing is selected
            }
        }


        location.setOnClickListener {
            showBottomSheet()
        }

        selectImage.setOnClickListener {
            ImagePicker.Companion.with(this).crop().start()
        }

        additionalInformation.setOnClickListener {
            if (hide) {
                hide_leaner.visibility = View.VISIBLE
                hide = false
            } else {
                hide_leaner.visibility = View.GONE
                hide = true
            }
        }


        sellLaptop.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("Laptops")
            val key = ref.push().key
            val model = LaptopsModel(key!!,imgUri.toString(),etTitle.text.toString(),etDescription.text.toString(),selectedManufactureItem,locationClickedWord,etPrice.text.toString(),newCondition,usedCondition,negoYes,negoNO,marchaYes,marchaNo,productLaptop,productAccessory,etModel.text.toString())
            ref.child(key!!).setValue(model)
            finish()
            Toast.makeText(this, "Data Uploaded Successfully", Toast.LENGTH_SHORT).show()
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
        negoYes = selectedNegoText.equals("Yes", ignoreCase = true)
        negoNO = selectedNegoText.equals("No", ignoreCase = true)

    }

    private fun onTextViewClicked2(view: View) {
        // Set the selected TextView based on the clicked view
        selectedMarchaTextView = (view as TextView)
        updateSelectedTextView2()
    }

    private fun updateSelectedTextView2() {
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

    private fun onTextViewClicked3(view: View) {
        // Set the selected TextView based on the clicked view
        selectedProductTextView = (view as TextView)
        updateSelectedTextView3()
    }

    private fun updateSelectedTextView3() {
        // Update the background and text color of the selected TextView
        val selectedColor = getColor(R.color.black)

        selectedProductTextView.setBackgroundResource(R.drawable.selected_background)
        selectedProductTextView.setTextColor(selectedColor)

        val otherTextView = if (selectedProductTextView.id == R.id.laptopProduct) {
            findViewById<TextView>(R.id.accessoryProduct)
        } else {
            findViewById<TextView>(R.id.laptopProduct)
        }

        otherTextView.setBackgroundResource(R.drawable.rounded_background)
        otherTextView.setTextColor(selectedColor)

        // Now you can use 'selectedTextView' to get the selected text
        selectedProductText = selectedProductTextView.text.toString()
        productLaptop = selectedProductText.equals("Laptop", ignoreCase = true)
        productAccessory = selectedProductText.equals("Accessory", ignoreCase = true)

    }

}