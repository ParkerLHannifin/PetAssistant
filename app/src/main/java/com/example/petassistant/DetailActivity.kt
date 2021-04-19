package com.example.petassistant

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DetailActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser!!
    var petID: String? = null
    var name = "???"
    var type = "???"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

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
            name = bundle.getString("name").toString()
            type = bundle.getString("type").toString()
        }

        petID?.let {
            db.collection(user.uid).document(petID!!).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
//                        firebase bug: after adding a pet, select the new pet from the list could result in null document
//                        petName.text = document.data?.get("name") as? String
//                        petType.text = document.data?.get("type") as? String
                        //use bundle data instead
                        petName.text = name
                        petType.text = type
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
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Notice")
                builder.setMessage("Are you sure to delete the pet?")
                builder.setPositiveButton("Yes") { dialog, which ->
                    petID?.let {
                        db.collection(user.uid).document(it)
                            .delete()
                            .addOnSuccessListener {
                                val resultIntent = Intent()
                                resultIntent.putExtra("rm_id", petID)
                                setResult(4, resultIntent)
                                Toast.makeText(
                                    this,
                                    "Deleted the pet successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Failed to delete the pet", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }
                }
                builder.setNeutralButton("No") { _, _ -> }
                val dialog = builder.create()
                dialog.show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}