package com.example.petassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddExActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ex)

        val type = findViewById<EditText>(R.id.enterExType)
        val duration = findViewById<EditText>(R.id.enterDuration)
        val date = findViewById<EditText>(R.id.exDate)
        val time = findViewById<EditText>(R.id.exTime)
        val cancel = findViewById<Button>(R.id.cancelExButton)
        val add = findViewById<Button>(R.id.addEx)

        cancel.setOnClickListener {
            finish()
        }

        add.setOnClickListener {
            if(type.text.toString().trim() != "" && duration.text.toString().trim() != "" && date.text.toString().trim() != "" && time.text.toString().trim() != "") {
                val db = FirebaseFirestore.getInstance()
                val ex: MutableMap<String, Any?> = HashMap()
                    ex["exType"] = type.text.toString()
                    ex["exDuration"] = duration.text.toString()
                    ex["exDate"] = date.text.toString()
                    ex["exTime"] = time.text.toString()
                    ex["id"] = auth.currentUser!!.uid
                    ex["name"] = intent.getStringExtra("name")
                db.collection("ex").add(ex)

                Toast.makeText(this, "Exercise recorded!", Toast.LENGTH_SHORT).show()
                finish()

            } else {
                Toast.makeText(this, "Fill in all the boxes!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}