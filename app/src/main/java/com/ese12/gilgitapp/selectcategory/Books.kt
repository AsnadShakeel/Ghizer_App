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
import com.ese12.gilgitapp.BooksModel
import com.ese12.gilgitapp.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.FirebaseDatabase
import com.nex3z.flowlayout.FlowLayout

class Books : AppCompatActivity() {
    private lateinit var selectedImages: ImageView
    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var etPrice: EditText
    private lateinit var etLocation: TextView
    private lateinit var newTextView: TextView
    private lateinit var usedTextView: TextView
    private lateinit var negoYes: TextView
    private lateinit var noNego: TextView
    private lateinit var sellNowBook: TextView

    var imgUri: Uri = Uri.parse("")
    var locationClickedWord: String = ""

    private var newCondition: Boolean = false
    private var usedCondition: Boolean = false

    private lateinit var selectedConditionTextView: TextView
    private lateinit var selectedConditionText: String

    private var yesNegoBool: Boolean = false
    private var noNegoBool: Boolean = false

    private lateinit var selectedNegoTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        selectedImages = findViewById(R.id.selectedImages)
        etTitle = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        etPrice = findViewById(R.id.etPrice)
        etLocation = findViewById(R.id.etLocation)
        newTextView = findViewById(R.id.New)
        usedTextView = findViewById(R.id.used)
        negoYes = findViewById(R.id.negoYes)
        noNego = findViewById(R.id.noNego)
        sellNowBook = findViewById(R.id.sellNowBook)

        selectedImages.setOnClickListener {
            ImagePicker.Companion.with(this).crop().start()
        }

        etLocation.setOnClickListener {
            showBottomSheet()
        }


        newTextView.setOnClickListener { onTextViewClicked(it) }
        usedTextView.setOnClickListener { onTextViewClicked(it) }

        selectedConditionTextView = newTextView
        updateSelectedTextView()

        negoYes.setOnClickListener { onTextViewClicked1(it) }
        noNego.setOnClickListener { onTextViewClicked1(it) }

        selectedNegoTextView = negoYes
        updateSelectedTextView1()

        sellNowBook.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("Books")
            val key = ref.push().key
            val model = BooksModel(
                key!!,
                imgUri.toString(),
                etTitle.text.toString(),
                etDescription.text.toString(),
                etPrice.text.toString(),
                locationClickedWord,
                newCondition,
                usedCondition,
                yesNegoBool,
                noNegoBool
            )
            ref.child(key).setValue(model)
            Toast.makeText(this, "Data Uploaded Sucessfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private lateinit var selectedNegoText: String


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
            findViewById<TextView>(R.id.noNego)
        } else {
            findViewById<TextView>(R.id.negoYes)
        }

        otherTextView.setBackgroundResource(R.drawable.rounded_background)
        otherTextView.setTextColor(selectedColor)

        // Now you can use 'selectedTextView' to get the selected text
        selectedNegoText = selectedNegoTextView.text.toString()
        yesNegoBool = selectedNegoText.equals("Yes", ignoreCase = true)
        noNegoBool = selectedNegoText.equals("No", ignoreCase = true)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            imgUri = data!!.data!!

            selectedImages.setImageURI(imgUri)

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
}