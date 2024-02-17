package com.ese12.gilgitapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit


class UserOtpVerifyActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "Nww"

        /** CountDown Timer */
        const val DELAY: Long = 60000
        const val INTERVAL: Long = 1000
    }

    private lateinit var etC1: EditText
    private lateinit var etC2: EditText
    private lateinit var etC3: EditText
    private lateinit var etC4: EditText
    private lateinit var etC5: EditText
    private lateinit var etC6: EditText
    private lateinit var tvMobile: TextView
    private lateinit var textView7: TextView
    private lateinit var btnVerify: TextView

    //    private var user: UserModel? = null
    private lateinit var tvResendBtn: TextView
    private lateinit var progressBarVerify: ProgressBar
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mResendingToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var mVerificationId: String
    private lateinit var mPhoneNumber: String
    private lateinit var mUserName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verify_user)

        // Find views by their IDs
        etC1 = findViewById(R.id.etC1)
        etC2 = findViewById(R.id.etC2)
        etC3 = findViewById(R.id.etC3)
        etC4 = findViewById(R.id.etC4)
        etC5 = findViewById(R.id.etC5)
        etC6 = findViewById(R.id.etC6)
        tvMobile = findViewById(R.id.tvMobile)
        textView7 = findViewById(R.id.textView7)
        btnVerify = findViewById(R.id.btnVerify)
        tvResendBtn = findViewById(R.id.tvResendBtn)
        progressBarVerify = findViewById(R.id.progressBarVerify)


        editTextInput()

        mAuth = Firebase.auth
        mPhoneNumber = intent.getStringExtra("phone").toString()
        mUserName = intent.getStringExtra("userName").toString()
        mVerificationId = intent.getStringExtra("verificationId").toString()
        mResendingToken = intent.getParcelableExtra("forceResendingToken")!!

//        user = UserModel(mUserName,mPhoneNumber)

        tvMobile.text = mPhoneNumber


        /** countDownTimer */
        countDownTimer()


        btnVerify.setOnClickListener {
            val c1 = etC1.text.toString().trim()
            val c2 = etC2.text.toString().trim()
            val c3 = etC3.text.toString().trim()
            val c4 = etC4.text.toString().trim()
            val c5 = etC5.text.toString().trim()
            val c6 = etC6.text.toString().trim()
            if (c1.isEmpty() || c2.isEmpty() || c3.isEmpty() || c4.isEmpty() || c5.isEmpty() || c6.isEmpty())
                Toast.makeText(this, "Filed Must Not be empty", Toast.LENGTH_SHORT).show()
            else {
                if (mVerificationId != null) {
                    val smsCode = "$c1$c2$c3$c4$c5$c6"
                    val credential = PhoneAuthProvider.getCredential(mVerificationId, smsCode)


                    Firebase.auth.signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.i(TAG, " if (task.isSuccessful)")
                                isVisible(true)
                                Toast.makeText(
                                    this,
                                    "Welcome... ${task.result}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(this,MainActivity::class.java))
                            } else {
                                isVisible(false)
                                Toast.makeText(this, "Otp is not Valid!", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }

    private fun countDownTimer() {
        countDownTimer = object : CountDownTimer(DELAY, INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                textView7.text = Callback(this@UserOtpVerifyActivity).elapsedCountDownTimer(
                    millisUntilFinished.div(
                        INTERVAL
                    )
                )
                tvResendBtn.isVisible = false
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onFinish() {
                textView7.text = "Don't get the OTP?"
                tvResendBtn.isVisible = true
                tvResendBtn.setTextColor(resources.getColor(R.color.red_400, theme))
                tvResendBtn.setOnClickListener {
                    isVisible(true)
                    forceResendingToken()
                }
            }
        }
        countDownTimer.start()
    }

    private fun forceResendingToken() {
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("TAG", "onVerificationCompleted: ${credential.smsCode}")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                isVisible(false)
                Toast.makeText(this@UserOtpVerifyActivity, e.localizedMessage, Toast.LENGTH_SHORT)
                    .show()

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
//                super.onCodeSent(verificationId, token)
                isVisible(false)
                Toast.makeText(
                    this@UserOtpVerifyActivity,
                    "Otp is Successfully Send!",
                    Toast.LENGTH_SHORT
                ).show()

                mResendingToken = token
                mVerificationId = verificationId
            }
        }

        PhoneAuthProvider.verifyPhoneNumber(
            PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(mPhoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .setForceResendingToken(mResendingToken)
                .build()
        )
    }

    private fun isVisible(visible: Boolean) {
        progressBarVerify.isVisible = visible
        btnVerify.isVisible = !visible
    }

    private fun editTextInput() {

        etC1.doOnTextChanged { text, _, count, _ ->
            if (count == 6) {
                etC1.setText(text!![0].toString())
                etC2.setText(text[1].toString())
                etC3.setText(text[2].toString())
                etC4.setText(text[3].toString())
                etC5.setText(text[4].toString())
                etC6.setText(text[5].toString())
            } else {
                etC2.requestFocus()
            }
        }
        etC2.doOnTextChanged { _, _, _, _ ->
            etC3.requestFocus()
        }
        etC3.doOnTextChanged { _, _, _, _ ->
            etC4.requestFocus()
        }
        etC4.doOnTextChanged { _, _, _, _ ->
            etC5.requestFocus()
        }
        etC5.doOnTextChanged { _, _, _, _ ->
            etC6.requestFocus()
        }
    }


}