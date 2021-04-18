package com.example.petassistant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

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
            AlertDialog.Builder(this)
                    .setTitle("Remove Pet")
                    .setMessage("Are you sure you want remove this item?")
                    .setPositiveButton("Yes") { _, _ ->

                        //REMOVE ITEM FROM DATABASE

                        startActivity(Intent(this, ViewActivity::class.java))
                    }
                    .setNegativeButton("Cancel", null).show()
        }
    }
}