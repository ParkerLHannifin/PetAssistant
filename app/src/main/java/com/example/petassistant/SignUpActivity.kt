package com.example.petassistant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

@Suppress("CascadeIf")
class SignUpActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        val emailTF = findViewById<EditText>(R.id.signUpEmail)
        val pwdTF = findViewById<EditText>(R.id.signUpPwd)
        val confirmPwd = findViewById<EditText>(R.id.confirmPwd)
        val signUpBtn = findViewById<Button>(R.id.signUpBtn)
        var pwdUpper = false
        var pwdLower = false
        var pwdNumber = false


        signUpBtn.setOnClickListener {
            var pwdLength = 0
            val emailMatch = android.util.Patterns.EMAIL_ADDRESS.matcher(emailTF.text).matches()
            val pwdMatch = pwdTF.text.toString() == confirmPwd.text.toString()
            if (emailMatch && pwdMatch) {
                for (letter in pwdTF.text.toString()) {
                    pwdLength++
                    if (letter.isUpperCase()) {
                        pwdUpper = true
                    } else if (letter.isLowerCase()) {
                        pwdLower = true
                    } else if (letter.isDigit()) {
                        pwdNumber = true
                    }
                }

                if (pwdUpper && pwdLower && pwdNumber && pwdLength >= 12) {
                    auth.createUserWithEmailAndPassword(
                        emailTF.text.toString(),
                        pwdTF.text.toString()
                    )
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "user created", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, HomeActivity::class.java))
                            }
                        }
                } else {
                    Toast.makeText(this, "Password does not meet requirements", Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (!emailMatch) {
                Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show()
            } else if (!pwdMatch) {
                Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }
}