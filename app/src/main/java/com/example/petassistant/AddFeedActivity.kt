package com.example.petassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class AddFeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
            //ADD FOOD TO DATABSE
        }
    }
}