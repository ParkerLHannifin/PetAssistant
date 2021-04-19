package com.example.petassistant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ViewAllActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)

        val back = findViewById<Button>(R.id.viewAllBack)
        val list = findViewById<ListView>(R.id.list)
        val elements = ArrayList<String>()
        val db = FirebaseFirestore.getInstance()
        var array = arrayOf("")

        db.collection("ex").get().addOnSuccessListener { docs ->
            for (i in docs) {
                if (i.data["name"] == intent.getStringExtra("name") && i.data["id"] == auth.currentUser!!.uid) {
                    elements.add("" + i.data["exDate"] + ", " + i.data["exTime"] + ", " + i.data["exType"] + ", " + i.data["exDuration"] + " minutes")
                }
            }

            db.collection("food").get().addOnSuccessListener { docs ->
                for (i in docs) {
                    if (i.data["name"] == intent.getStringExtra("name") && i.data["id"] == auth.currentUser!!.uid) {
                        elements.add("" + i.data["foodDate"] + ", " + i.data["foodTime"] + ", " + i.data["foodType"])
                    }
                }

                db.collection("med").get().addOnSuccessListener { docs ->
                    for (i in docs) {
                        if (i.data["name"] == intent.getStringExtra("name") && i.data["id"] == auth.currentUser!!.uid) {
                            elements.add("" + i.data["medDate"] + ", " + i.data["medTime"] + ", " + i.data["medName"] + ", " + i.data["medDose"])
                        }
                    }
                    
                    list.adapter = MyAdapter(elements, this)
                    array = elements.toTypedArray()
                }
            }
        }

        list.setOnItemClickListener { _, _, position, _ ->

            AlertDialog.Builder(this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this?")
                .setPositiveButton("Yes") { _, _ ->

                    db.collection("ex").get().addOnSuccessListener { docs ->
                        for (i in docs) {
                            if (array[position] == "" + i.data["exDate"] + ", " + i.data["exTime"] + ", " + i.data["exType"] + ", " + i.data["exDuration"] + " minutes") {
                                db.collection("ex").document(i.id).delete()
                            }
                        }
                    }
                    db.collection("food").get().addOnSuccessListener { docs ->
                        for (i in docs) {
                            if (array[position] == "" + i.data["foodDate"] + ", " + i.data["foodTime"] + ", " + i.data["foodType"]) {
                                db.collection("food").document(i.id).delete()
                            }
                        }
                    }
                    db.collection("med").get().addOnSuccessListener { docs ->
                        for (i in docs) {
                            if (array[position] == "" + i.data["medDate"] + ", " + i.data["medTime"] + ", " + i.data["medName"] + ", " + i.data["medDose"]) {
                                db.collection("med").document(i.id).delete()
                            }
                        }
                    }

                    finish()
                }
                .setNegativeButton("Cancel", null).show()
        }

        back.setOnClickListener {
            finish()
        }
    }

    class MyAdapter(private var data: ArrayList<String>, var context: Context) : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val tv = TextView(context)
            tv.text = data[position]
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30F)
            tv.setPadding(10, 10, 10, 10)
            return tv
        }

        override fun getCount(): Int {
            return data.size
        }

        override fun getItem(position: Int): Any {
            return 0
        }

        override fun getItemId(position: Int): Long {
            return 0
        }
    }
}

