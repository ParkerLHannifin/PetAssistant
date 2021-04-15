package com.example.petassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class AddMedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_med)

        val enterName = findViewById<EditText>(R.id.enterMedName)
        val enterDose = findViewById<EditText>(R.id.enterMedDose)
        val cancel = findViewById<Button>(R.id.cancelMedButton)
        val add = findViewById<Button>(R.id.AddMed)

        cancel.setOnClickListener {
            finish()
        }

        add.setOnClickListener {
            //ADD MEDICINE TO DATABASE
        }
    }
}