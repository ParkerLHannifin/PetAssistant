package com.example.petassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class AddExActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ex)

        val type = findViewById<EditText>(R.id.enterExType)
        val duration = findViewById<EditText>(R.id.enterDuration)
        val cancel = findViewById<Button>(R.id.cancelExButton)
        val add = findViewById<Button>(R.id.addEx)

        cancel.setOnClickListener {
            finish()
        }

        add.setOnClickListener {
            //ADD EXERCISE TO DATABASE
        }
    }
}