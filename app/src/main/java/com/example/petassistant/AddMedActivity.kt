package com.example.petassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddMedActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_med)

        val enterName = findViewById<EditText>(R.id.enterMedName)
        val enterDose = findViewById<EditText>(R.id.enterMedDose)
        val date = findViewById<EditText>(R.id.medDate)
        val time = findViewById<EditText>(R.id.medTime)
        val cancel = findViewById<Button>(R.id.cancelMedButton)
        val add = findViewById<Button>(R.id.AddMed)

        cancel.setOnClickListener {
            finish()
        }

        add.setOnClickListener {
            if(enterName.text.toString().trim() != "" && enterDose.text.toString().trim() != "" && date.text.toString().trim() != "" && time.text.toString().trim() != "") {
                val db = FirebaseFirestore.getInstance()
                val med: MutableMap<String, Any?> = HashMap()
                    med["medName"] = enterName.text.toString()
                    med["medDose"] = enterDose.text.toString()
                    med["medDate"] = date.text.toString()
                    med["medTime"] = time.text.toString()
                    med["id"] = auth.currentUser!!.uid
                    med["name"] = intent.getStringExtra("name")
                db.collection("med").add(med)

                Toast.makeText(this, "Medicine recorded!", Toast.LENGTH_SHORT).show()
                finish()

            } else {
                Toast.makeText(this, "Fill in all the boxes!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}