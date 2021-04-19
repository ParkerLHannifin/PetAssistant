package com.example.petassistant

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class AddActivity : AppCompatActivity() {
    private lateinit var nameInput: EditText
    private lateinit var typeInput: EditText
    private lateinit var saveBtn: Button
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        findViewById<Button>(R.id.save_btn).setOnClickListener {
            addPet()
        }
        nameInput = findViewById(R.id.name_input)
        typeInput = findViewById(R.id.type_input)
        saveBtn = findViewById(R.id.save_btn)
        saveBtn.setOnClickListener {
            addPet()
        }
    }

    private fun addPet() {
        val resultIntent = Intent()
        val user = FirebaseAuth.getInstance().currentUser!!

        if (nameInput.text.isNullOrEmpty() || typeInput.text.isNullOrEmpty()) {
            Toast.makeText(this, "Input cannot be empty", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else {
            val id = UUID.randomUUID().toString()
            val name = nameInput.text.toString()
            val type = typeInput.text.toString()
            val newPet = Pet(id = id, name = name, type = type)

            db.collection(user.uid)
                .add(newPet)
                .addOnSuccessListener {
                    resultIntent.putExtra("id", id)
                    resultIntent.putExtra("name", name)
                    resultIntent.putExtra("type", type)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
                .addOnFailureListener {
                    Log.d("aaa", "添加失败")
                    finish()
                    Toast.makeText(this, "Failed to save pet", Toast.LENGTH_SHORT).show()
                }
        }
    }
}