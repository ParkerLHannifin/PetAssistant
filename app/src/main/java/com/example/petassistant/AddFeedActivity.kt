package com.example.petassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

lateinit var auth: FirebaseAuth

class AddFeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_feed)

        val type = findViewById<EditText>(R.id.enterFoodType)
        val date = findViewById<EditText>(R.id.enterDate)
        val time = findViewById<EditText>(R.id.enterTime)
        val cancel = findViewById<Button>(R.id.cancelFoodButton)
        val add = findViewById<Button>(R.id.addFood)

        cancel.setOnClickListener {
            finish()
        }

        add.setOnClickListener {
            if(type.text.toString().trim() != "" && date.text.toString().trim() != "" && time.text.toString().trim() != "") {
                val db = FirebaseFirestore.getInstance()
                val food: MutableMap<String, Any> = HashMap()
                food["foodType"] = type.text.toString()
                food["foodDate"] = date.text.toString()
                food["foodTime"] = time.text.toString()
                food["id"] = auth.currentUser.uid
                food["type"] = "food"
                db.collection("petInfo").add(food)

            } else {
                Toast.makeText(this, "Fill in all the boxes!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}