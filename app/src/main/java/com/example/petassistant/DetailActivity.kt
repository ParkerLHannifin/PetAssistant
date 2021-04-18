package com.example.petassistant

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val db = FirebaseFirestore.getInstance()
        var petID: String? = null

        val petName = findViewById<TextView>(R.id.detail_pet_name)
        val petType = findViewById<TextView>(R.id.detail_pet_type)
        val addExBtn = findViewById<Button>(R.id.add_exercise)
        val addMdBtn = findViewById<Button>(R.id.add_medical)
        val addFdBtn = findViewById<Button>(R.id.add_feeding)

        addExBtn.setOnClickListener {
            //go to add exercise...
        }

        addMdBtn.setOnClickListener {
            //go to add medical...
        }

        addFdBtn.setOnClickListener {
            //go to add feeding
        }

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            petID = bundle.getString("petID")
        }
        petID?.let {
            db.collection("yong").document(petID).get()
                    .addOnSuccessListener { doc ->
                        if (doc != null) {
                            Log.d("aaa", "获取l宠物")
                            petName.text = doc.data?.get("name") as String
                            petType.text = doc.data?.get("type") as String
                        } else {
                            Toast.makeText(this, "Pet does not exist", Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(this, "Failed to get pet", Toast.LENGTH_SHORT).show()
                    }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                //delete item
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}