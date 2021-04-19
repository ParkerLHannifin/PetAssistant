package com.example.petassistant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        auth = FirebaseAuth.getInstance()
        var signUpBtnHome = findViewById<Button>(R.id.signUpBtnHome)
        var logInBtn = findViewById<Button>(R.id.signInBtn)
        var emailTF = findViewById<EditText>(R.id.signInEmail)
        var pwdTF = findViewById<EditText>(R.id.signInPwd)

        signUpBtnHome.setOnClickListener(){
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        logInBtn.setOnClickListener(){
            if(emailTF.text.toString() == "" || pwdTF.text.toString() == ""){
                Toast.makeText(this, "Invalid Email/Password", Toast.LENGTH_SHORT)
                        .show()
            }
            else {
                auth.signInWithEmailAndPassword(emailTF.text.toString(), pwdTF.text.toString())
                        .addOnCompleteListener() {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "Signed In!", Toast.LENGTH_SHORT)
                                        .show()
                                startActivity(Intent(this, ViewPetActivity::class.java))
                            } else {
                                Toast.makeText(this, "Invalid Email/Password", Toast.LENGTH_SHORT)
                                        .show()
                            }
                        }
            }

        }
    }
}