package com.ese12.gilgitapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.hbb20.CountryCodePicker
import java.util.concurrent.TimeUnit

class LoginWithPhoneNumber : AppCompatActivity() {


    private lateinit var btnSend: Button
    private lateinit var phoneNumberWithCountryCode:String
    private lateinit var etPhone: EditText
    private lateinit var userName: EditText
    private lateinit var ccp: CountryCodePicker
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mResendingToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var mVerificationId: String? = null

    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_with_phone_number)
        ccp = findViewById(R.id.ccp)
        mAuth = Firebase.auth
        btnSend = findViewById(R.id.login)
        etPhone = findViewById(R.id.etPhone)
        userName = findViewById(R.id.userName)

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")


        btnSend.setOnClickListener {
            Toast.makeText(this, "Please Wait...", Toast.LENGTH_SHORT).show()
            val number = etPhone.text.toString().trim()
            val countryCode = ccp.selectedCountryCodeWithPlus
            val phoneNumber = getPhoneNumber(number)
            phoneNumberWithCountryCode = "$countryCode$phoneNumber"
            otpSend(phoneNumberWithCountryCode)
        }

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("TAG", "onVerificationCompleted: ${credential.smsCode}")
                // You can automatically sign in the user here if verification is completed instantly
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.i("TAG",  e.localizedMessage)
                Toast.makeText(this@LoginWithPhoneNumber, e.localizedMessage, Toast.LENGTH_SHORT).show()
                Toast.makeText(this@LoginWithPhoneNumber, "Verification Failed", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Toast.makeText(this@LoginWithPhoneNumber, "OTP has been successfully sent.", Toast.LENGTH_SHORT).show()

                mVerificationId = verificationId
                mResendingToken = token

                startActivity(
                    Intent(this@LoginWithPhoneNumber, UserOtpVerifyActivity::class.java)
                        .putExtra("phone", phoneNumberWithCountryCode)
                        .putExtra("verificationId", mVerificationId)
                        .putExtra("userName", userName.text.toString())
                        .putExtra("forceResendingToken", mResendingToken)

                )
            }
        }

    }

    private fun getPhoneNumber(phone: String): String? {
        val phoneNumberPattern = "^[0-9]{10}\$"
        if (phone.isEmpty()) {
            Toast.makeText(this, "Phone number is required!", Toast.LENGTH_SHORT).show()
            return null
        } else if (!phone.matches(phoneNumberPattern.toRegex())) {
            Toast.makeText(this, "Please enter a valid phone number!", Toast.LENGTH_SHORT).show()
            return null
        } else {
            return phone
        }
    }

    private fun otpSend(phoneNumberWithCountryCode: String) {
        PhoneAuthProvider.verifyPhoneNumber(
            PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumberWithCountryCode)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build()
        )
    }
}