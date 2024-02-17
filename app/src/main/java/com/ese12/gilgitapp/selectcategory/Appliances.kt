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
import com.ese12.gilgitapp.AppliancesModel
import com.ese12.gilgitapp.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.FirebaseDatabase
import com.nex3z.flowlayout.FlowLayout

class Appliances : AppCompatActivity() {
    private lateinit var selectedImage: ImageView
    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var etManufacture: EditText
    private lateinit var etLocation: TextView
    private lateinit var etPrice: EditText
    private lateinit var newTextView: TextView
    private lateinit var usedTextView: TextView
    private lateinit var negoYes: TextView
    private lateinit var negoNo: TextView
    private lateinit var additional: TextView
    private lateinit var linear: LinearLayout
    private lateinit var etItemColor: EditText
    private lateinit var sellNow: TextView

    private var newCondition: Boolean = false
    private var usedCondition: Boolean = false

    var imgUri:Uri = Uri.parse("")
    var locationClickedWord:String=""
    private var hide: Boolean = true

    private var yesNego: Boolean = false
    private var noNego: Boolean = false

    private lateinit var selectedNegoTextView: TextView
    private lateinit var selectedNegoText: String

    private lateinit var selectedConditionTextView: TextView
    private lateinit var selectedConditionText: String
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appliances)
        selectedImage = findViewById(R.id.selectedImage)
        etTitle = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        etManufacture = findViewById(R.id.etManufacture)
        etLocation = findViewById(R.id.etLocation)
        etPrice = findViewById(R.id.etPrice)
        newTextView = findViewById(R.id.newCondition)
        usedTextView = findViewById(R.id.used)
        negoYes = findViewById(R.id.negoYes)
        negoNo = findViewById(R.id.negoNo)
        additional = findViewById(R.id.additional)
        linear = findViewById(R.id.linear)
        etItemColor = findViewById(R.id.etItemColor)
        sellNow = findViewById(R.id.sellNow)

        etLocation.setOnClickListener {
            showBottomSheet()
        }

        additional.setOnClickListener {
            if (hide) {
                linear.visibility = View.VISIBLE
                hide = false
            } else {
                linear.visibility = View.GONE
                hide = true
            }
        }

        newTextView.setOnClickListener { onTextViewClicked(it) }
        usedTextView.setOnClickListener { onTextViewClicked(it) }

        selectedConditionTextView = newTextView
        updateSelectedTextView()


        negoYes.setOnClickListener { onTextViewClicked2(it) }
        negoNo.setOnClickListener { onTextViewClicked2(it) }

        selectedNegoTextView = negoYes
        updateSelectedTextView2()

        selectedImage.setOnClickListener {
            ImagePicker.Companion.with(this).crop().start()
        }

        sellNow.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("Appliances")
            val key = ref.push().key
            val model = AppliancesModel(key!!,imgUri.toString(),etTitle.text.toString(),etManufacture.text.toString(),etDescription.text.toString(),locationClickedWord,etPrice.text.toString(),newCondition,usedCondition,yesNego,noNego,etItemColor.text.toString())
            ref.child(key).setValue(model)
            Toast.makeText(this, "Data Uploaded Sucessfully", Toast.LENGTH_SHORT).show()
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

        val otherTextView = if (selectedConditionTextView.id == R.id.newCondition) {
            findViewById<TextView>(R.id.used)
        } else {
            findViewById<TextView>(R.id.newCondition)
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
        selectedNegoTextView = (view as TextView)
        updateSelectedTextView2()
    }

    private fun updateSelectedTextView2() {
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
                    etLocation.text = locationClickedWord
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

            selectedImage.setImageURI(imgUri)

        } catch (e: Exception) {

        }
    }

}