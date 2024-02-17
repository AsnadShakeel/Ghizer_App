package com.ese12.gilgitapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ese12.gilgitapp.Activities.SignUpActivity
import com.ese12.gilgitapp.Fragments.AccountFragment
import com.ese12.gilgitapp.Fragments.HomeFragment
import com.ese12.gilgitapp.Fragments.MessagesFragment
import com.ese12.gilgitapp.Fragments.NotificationFragment
import com.ese12.gilgitapp.selectcategory.*
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        bottomAppBar = findViewById(R.id.bottomAppBar)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fragmentContainer = findViewById(R.id.fragmentContainer)
        fab = findViewById(R.id.fab)

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        fab.setOnClickListener {
            showDialog()
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_account -> {
                    replaceFragment(AccountFragment())
                    true
                }
                R.id.nav_messages -> {
                    replaceFragment(MessagesFragment())
                    true
                }
                R.id.nav_notifi -> {
                    replaceFragment(NotificationFragment())
                    true
                }
                // Handle other menu items here

                else -> false
            }
        }
        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottomsheetlayout)
        val buyer_request = dialog.findViewById<LinearLayout>(R.id.buyer_request)
        val cars_suvs = dialog.findViewById<LinearLayout>(R.id.cars_suvs)
        val bikes = dialog.findViewById<LinearLayout>(R.id.bikes)
        val mobile_accessories = dialog.findViewById<LinearLayout>(R.id.mobile_accessories)
        val laptops_accessories = dialog.findViewById<LinearLayout>(R.id.laptops_accessories)
        val house = dialog.findViewById<LinearLayout>(R.id.house)
        val pets = dialog.findViewById<LinearLayout>(R.id.pets)
        val office = dialog.findViewById<LinearLayout>(R.id.office)
        val shop = dialog.findViewById<LinearLayout>(R.id.shop)
        val plot = dialog.findViewById<LinearLayout>(R.id.plot)
        val appliances = dialog.findViewById<LinearLayout>(R.id.appliances)
        val furniture = dialog.findViewById<LinearLayout>(R.id.furniture)
        val fashion_beauty = dialog.findViewById<LinearLayout>(R.id.fashion_beauty)
        val books = dialog.findViewById<LinearLayout>(R.id.books)
        val dry_friut = dialog.findViewById<LinearLayout>(R.id.dry_friut)
        val other = dialog.findViewById<LinearLayout>(R.id.other)

        buyer_request.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, BuyerRequest::class.java))
            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
        cars_suvs.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, CarsSuvs::class.java))
            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
        bikes.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, Bikes::class.java))
            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
        mobile_accessories.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, MobileAccessories::class.java))
            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
        laptops_accessories.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, LaptopsAccessories::class.java))
            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
        house.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, House::class.java))
            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
        pets.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, Pets::class.java))
            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
        office.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, Office::class.java))
            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
        shop.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, Shop::class.java))
            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
        plot.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, Plot::class.java))
            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
        appliances.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, Appliances::class.java))
            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
        furniture.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, Furniture::class.java))
            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
        fashion_beauty.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, FashionBeauty::class.java))
            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
        books.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, Books::class.java))
            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
        dry_friut.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, Dry_Friut::class.java))
            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
        other.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, Other::class.java))
            }else{
                startActivity(Intent(this, SignUpActivity::class.java))
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

}