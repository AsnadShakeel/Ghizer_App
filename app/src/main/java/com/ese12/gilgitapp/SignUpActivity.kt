package com.ese12.gilgitapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.ese12.gilgitapp.selectcategory.BuyerRequest
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignUpActivity : AppCompatActivity() {
    private var RCSIGNIN: Int = 123
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var googleLoginBtn: Button
    private lateinit var phoneLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()
        googleLoginBtn = findViewById(R.id.googleLoginBtn)
        phoneLogin = findViewById(R.id.phoneLogin)
        googleLoginBtn.setOnClickListener { loginUser() }
        phoneLogin.setOnClickListener { startActivity(Intent(this,LoginWithPhoneNumber::class.java)) }
    }

    private fun loginUser() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("304729930591-771mr2c6u759skvi5bj13vkr8ev10pcd.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RCSIGNIN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RCSIGNIN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result?.isSuccess!!) {
                val acct = result.signInAccount
                firebaseAuthWithGoogle(acct)
            } else {
                Toast.makeText(this, "There was a trouble signing-in Please try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(acct!!.idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            run {
                if (task.isSuccessful) {
                    saveEmailInSharedPref(acct)
                    startActivity(Intent(this@SignUpActivity,BuyerRequest::class.java))
                }else {
                    Toast.makeText(this@SignUpActivity,"Something went wrong, Please try again!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun saveEmailInSharedPref(acct: GoogleSignInAccount?){
        val preferences = getSharedPreferences(getString(R.string.preference_name), AppCompatActivity.MODE_PRIVATE)
        preferences?.edit()?.putString("email",acct?.email.toString())?.apply()
    }

}