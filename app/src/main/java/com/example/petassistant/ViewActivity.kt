package com.example.petassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView

class ViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        val list = findViewById<ListView>(R.id.list)
        val back = findViewById<Button>(R.id.back)

        //FILL LIST WITH RECENT ACTIVITIES

        back.setOnClickListener {
            finish()
        }

        list.setOnClickListener {
            //DELETE ALERT
        }
    }
}