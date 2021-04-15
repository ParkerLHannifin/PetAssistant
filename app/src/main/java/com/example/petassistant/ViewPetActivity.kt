package com.example.petassistant

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class ViewPetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pet)

        val remove = findViewById<Button>(R.id.removeButton)
        val icon = findViewById<ImageView>(R.id.icon)
        val name = findViewById<TextView>(R.id.nameLabel)
        val addMed = findViewById<Button>(R.id.addMedButton)
        val addEx = findViewById<Button>(R.id.addExButton)
        val addFeed = findViewById<Button>(R.id.addFeedButton)
        val view = findViewById<Button>(R.id.viewButton)

        //CHANGE PET PICTURE

        remove.setOnClickListener {
            AlertDialog.Builder(this)
                    .setTitle("Remove Pet")
                    .setMessage("Are you sure you want remove this pet?")
                    .setPositiveButton("Yes") { _, _ ->

                        //REMOVE PET FROM DATABASE

                        startActivity(Intent(this, MainActivity::class.java))
                    }
                    .setNegativeButton("Cancel", null).show()
        }

        addMed.setOnClickListener {
            startActivity(Intent(this, AddMedActivity::class.java))
        }

        addEx.setOnClickListener {
            startActivity(Intent(this, AddExActivity::class.java))
        }

        addFeed.setOnClickListener {
            startActivity(Intent(this, AddFeedActivity::class.java))
        }

        view.setOnClickListener {
            startActivity(Intent(this, ViewActivity::class.java))
        }
    }
}