package com.ese12.gilgitapp.selectcategory

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
import com.ese12.gilgitapp.Models.PetsModel
import com.ese12.gilgitapp.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.FirebaseDatabase
import com.nex3z.flowlayout.FlowLayout

class Pets : AppCompatActivity() {
    private lateinit var selectImage: ImageView
    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var location: TextView
    private lateinit var etPetPrice: EditText
    private lateinit var male: TextView
    private lateinit var feMale: TextView
    private lateinit var additionalInformation: TextView
    private lateinit var linear: LinearLayout
    private lateinit var negoYes: TextView
    private lateinit var negoNo: TextView
    private lateinit var petBreed: EditText
    private lateinit var petAge: EditText
    private lateinit var sellNowPets: TextView

    var imgUri:Uri = Uri.parse("")
    var locationClickedWord:String=""
    var hide:Boolean=true

    private var genderMale: Boolean = false
    private var genderFeMale: Boolean = false

    private lateinit var selectedGenderTextView: TextView
    private var selectedGenderText: String = ""

    private var negoYesBool: Boolean = false
    private var negoNoBool: Boolean = false

    private lateinit var selectedNegoTextView: TextView
    private var selectedNegoText: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pets)

        selectImage = findViewById(R.id.selectImage)
        etTitle = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        location = findViewById(R.id.location)
        etPetPrice = findViewById(R.id.etPetPrice)
        male = findViewById(R.id.male)
        feMale = findViewById(R.id.feMale)
        additionalInformation = findViewById(R.id.additionalInformation)
        linear = findViewById(R.id.linear)
        negoYes = findViewById(R.id.negoYes)
        negoNo = findViewById(R.id.negoNo)
        petBreed = findViewById(R.id.petBreed)
        petAge = findViewById(R.id.petAge)
        sellNowPets = findViewById(R.id.sellNowPets)

        additionalInformation.setOnClickListener {
            if (hide) {
                linear.visibility = View.VISIBLE
                hide = false
            } else {
                linear.visibility = View.GONE
                hide = true
            }
        }

        location.setOnClickListener {
            showBottomSheet()
        }

        selectImage.setOnClickListener {
            ImagePicker.Companion.with(this).crop().start()
        }

        male.setOnClickListener { onTextViewClicked(it) }
        feMale.setOnClickListener { onTextViewClicked(it) }

        selectedGenderTextView = male
        updateSelectedTextView()

        negoYes.setOnClickListener { onTextViewClicked1(it) }
        negoNo.setOnClickListener { onTextViewClicked1(it) }

        selectedNegoTextView = negoYes
        updateSelectedTextView1()

        sellNowPets.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("Pets")
            val key = ref.push().key
            val model = PetsModel(key!!,imgUri.toString(),etTitle.text.toString(),etDescription.text.toString(),etPetPrice.text.toString(),
            locationClickedWord,genderMale,genderFeMale,negoYesBool,negoNoBool,petBreed.text.toString(),petAge.text.toString())
            ref.child(key!!).setValue(model)
            Toast.makeText(this, "Data Uploaded Sucessfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun onTextViewClicked(view: View) {
        // Set the selected TextView based on the clicked view
        selectedGenderTextView = (view as TextView)
        updateSelectedTextView()
    }

    private fun updateSelectedTextView() {
        // Update the background and text color of the selected TextView
        val selectedColor = getColor(R.color.black)

        selectedGenderTextView.setBackgroundResource(R.drawable.selected_background)
        selectedGenderTextView.setTextColor(selectedColor)

        val otherTextView = if (selectedGenderTextView.id == R.id.male) {
            findViewById<TextView>(R.id.feMale)
        } else {
            findViewById<TextView>(R.id.male)
        }

        otherTextView.setBackgroundResource(R.drawable.rounded_background)
        otherTextView.setTextColor(selectedColor)

        // Now you can use 'selectedTextView' to get the selected text
        selectedGenderText = selectedGenderTextView.text.toString()
        genderMale = selectedGenderText.equals("Male", ignoreCase = true)
        genderFeMale = selectedGenderText.equals("Female", ignoreCase = true)
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
        negoYesBool = selectedNegoText.equals("Yes", ignoreCase = true)
        negoNoBool = selectedNegoText.equals("No", ignoreCase = true)
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

}