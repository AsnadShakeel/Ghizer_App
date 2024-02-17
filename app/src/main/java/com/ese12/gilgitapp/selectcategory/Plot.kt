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
import com.ese12.gilgitapp.PlotModel
import com.ese12.gilgitapp.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.FirebaseDatabase
import com.nex3z.flowlayout.FlowLayout

class Plot : AppCompatActivity() {
    private lateinit var selectedImage: ImageView
    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var etPrice: EditText
    private lateinit var etLocation: TextView
    private lateinit var rent: TextView
    private lateinit var sale: TextView
    private lateinit var etSecuirtyDeposit: EditText
    private lateinit var additional: TextView
    private lateinit var linear: LinearLayout
    private lateinit var tvKanal: TextView
    private lateinit var tvMarla: TextView
    private lateinit var negoYes: TextView
    private lateinit var negoNo: TextView
    private lateinit var tvResidential: TextView
    private lateinit var tvCommercial: TextView
    private lateinit var tvAgricultural: TextView
    private lateinit var etPersonName: EditText
    private lateinit var etContactNumber: EditText
    private lateinit var sellNowPlot: TextView

    var imgUri:Uri = Uri.parse("")
    var locationClickedWord:String=""

    private var forSaleBool: Boolean = false
    private var forRentBool: Boolean = false
    private var hide: Boolean = true

    private lateinit var selectedForSaleTextView: TextView
    private lateinit var selectedForSaleText: String

    private var forKanalBool: Boolean = false
    private var forMarlaBool: Boolean = false

    private lateinit var selectedForKanalTextView: TextView
    private lateinit var selectedForKanalText: String

    private var yesNego: Boolean = false
    private var noNego: Boolean = false

    private lateinit var selectedNegoTextView: TextView
    private lateinit var selectedNegoText: String

    private var resid: Boolean = false
    private var commer: Boolean = false
    private var aggricul: Boolean = false

    private lateinit var selectedResidTextView: TextView
    private lateinit var selectedResidText: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plot)

        selectedImage = findViewById(R.id.selectedImage)
        etTitle = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        etPrice = findViewById(R.id.etPrice)
        etLocation = findViewById(R.id.etLocation)
        rent = findViewById(R.id.rent)
        sale = findViewById(R.id.sale)
        etSecuirtyDeposit = findViewById(R.id.etSecuirtyDeposit)
        additional = findViewById(R.id.additional)
        linear = findViewById(R.id.linear)
        tvKanal = findViewById(R.id.tvKanal)
        tvMarla = findViewById(R.id.tvMarla)
        negoYes = findViewById(R.id.negoYes)
        negoNo = findViewById(R.id.negoNo)
        tvResidential = findViewById(R.id.tvResidential)
        tvCommercial = findViewById(R.id.tvCommercial)
        tvAgricultural = findViewById(R.id.tvAgricultural)
        etPersonName = findViewById(R.id.etPersonName)
        etContactNumber = findViewById(R.id.etContactNumber)
        sellNowPlot = findViewById(R.id.sellNowPlot)

        additional.setOnClickListener {
            if (hide) {
                linear.visibility = View.VISIBLE
                hide = false
            } else {
                linear.visibility = View.GONE
                hide = true
            }
        }


        selectedImage.setOnClickListener {
            ImagePicker.Companion.with(this).crop().start()
        }

        etLocation.setOnClickListener {
            showBottomSheet()
        }

        sale.setOnClickListener { onTextViewClicked(it) }
        rent.setOnClickListener { onTextViewClicked(it) }

        selectedForSaleTextView = sale
        updateSelectedTextView()

        tvKanal.setOnClickListener { onTextViewClicked1(it) }
        tvMarla.setOnClickListener { onTextViewClicked1(it) }

        selectedForKanalTextView = tvKanal
        updateSelectedTextView1()


        negoYes.setOnClickListener { onTextViewClicked2(it) }
        negoNo.setOnClickListener { onTextViewClicked2(it) }

        selectedNegoTextView = negoYes
        updateSelectedTextView2()

        tvResidential.setOnClickListener { onTextViewClicked3(it) }
        tvCommercial.setOnClickListener { onTextViewClicked3(it) }
        tvAgricultural.setOnClickListener { onTextViewClicked3(it) }

        selectedResidTextView = tvResidential
        updateSelectedTextView3()

        sellNowPlot.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("Plots")
            val key = ref.push().key
            val model = PlotModel(key!!,imgUri.toString(),etTitle.text.toString(),etDescription.text.toString(),locationClickedWord,etPrice.text.toString(),forRentBool,forSaleBool,forKanalBool,forMarlaBool,resid,commer,aggricul,etPersonName.text.toString(),etContactNumber.text.toString())
            ref.child(key).setValue(model)
            Toast.makeText(this, "Data Uploaded Successfully", Toast.LENGTH_SHORT).show()
            finish()
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

        val otherTextView = if (selectedForSaleTextView.id == R.id.sale) {
            findViewById<TextView>(R.id.rent)
        } else {
            findViewById<TextView>(R.id.sale)
        }

        otherTextView.setBackgroundResource(R.drawable.rounded_background)
        otherTextView.setTextColor(selectedColor)

        // Now you can use 'selectedTextView' to get the selected text
        selectedForSaleText = selectedForSaleTextView.text.toString()
        forSaleBool = selectedForSaleText.equals("For Sale", ignoreCase = true)
        forRentBool = selectedForSaleText.equals("For Rent", ignoreCase = true)

    }


    private fun onTextViewClicked1(view: View) {
        // Set the selected TextView based on the clicked view
        selectedForKanalTextView = (view as TextView)
        updateSelectedTextView1()
    }

    private fun updateSelectedTextView1() {
        // Update the background and text color of the selected TextView
        val selectedColor = getColor(R.color.black)

        selectedForKanalTextView.setBackgroundResource(R.drawable.selected_background)
        selectedForKanalTextView.setTextColor(selectedColor)

        val otherTextView = if (selectedForKanalTextView.id == R.id.tvKanal) {
            findViewById<TextView>(R.id.tvMarla)
        } else {
            findViewById<TextView>(R.id.tvKanal)
        }

        otherTextView.setBackgroundResource(R.drawable.rounded_background)
        otherTextView.setTextColor(selectedColor)

        // Now you can use 'selectedTextView' to get the selected text
        selectedForKanalText = selectedForKanalTextView.text.toString()
        forKanalBool = selectedForKanalText.equals("Kanal", ignoreCase = true)
        forMarlaBool = selectedForKanalText.equals("Marla", ignoreCase = true)

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

    private fun onTextViewClicked3(view: View) {
        // Set the selected TextView based on the clicked view
        selectedResidTextView = view as TextView
        updateSelectedTextView3()
    }

    private fun updateSelectedTextView3() {
        // Update the background and text color of the selected TextView
        val selectedColor = getColor(R.color.black)

        selectedResidTextView.setBackgroundResource(R.drawable.selected_background)
        selectedResidTextView.setTextColor(selectedColor)

        val otherTextViews = listOf(
            tvResidential,
            tvCommercial,
            tvAgricultural,
        )

        // Iterate through other TextViews to find the unselected one
        for (textView in otherTextViews) {
            if (textView != selectedResidTextView) {
                textView.setBackgroundResource(R.drawable.rounded_background)
                textView.setTextColor(selectedColor)
            }
        }

        // Now you can use 'selectedTextView' to get the selected text
        selectedResidText = selectedResidTextView.text.toString()

        resid = selectedResidText.equals("7 Days", ignoreCase = true)
        commer = selectedResidText.equals("15 Days", ignoreCase = true)
        aggricul = selectedResidText.equals("30 Days", ignoreCase = true)

        // You can store this selectedText in a variable or use it as needed
    }

}