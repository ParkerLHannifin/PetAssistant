package com.example.petassistant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {

            val signUpBtnHome = findViewById<Button>(R.id.signUpBtnHome)
            val logInBtn = findViewById<Button>(R.id.signInBtn)
            val emailTF = findViewById<EditText>(R.id.signInEmail)
            val pwdTF = findViewById<EditText>(R.id.signInPwd)

            signUpBtnHome.setOnClickListener {
                startActivity(Intent(this, SignUpActivity::class.java))
            }

            logInBtn.setOnClickListener {
                if (emailTF.text.toString() == "" || pwdTF.text.toString() == "") {
                    Toast.makeText(this, "Invalid Email/Password", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    auth.signInWithEmailAndPassword(emailTF.text.toString(), pwdTF.text.toString())
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "Signed In!", Toast.LENGTH_SHORT)
                                    .show()
                                startActivity(Intent(this, HomeActivity::class.java))
                            } else {
                                Toast.makeText(this, "Invalid Email/Password", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }

            }
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}