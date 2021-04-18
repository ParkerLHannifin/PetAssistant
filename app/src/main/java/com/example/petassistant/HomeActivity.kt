package com.example.petassistant

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {
    private val resultCode = 1
    private val myPets = mutableListOf<Pet>()
    private lateinit var adapter: PetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        adapter = PetAdapter { pet -> selectPet(pet) }
        recyclerView.adapter = adapter

        val db = FirebaseFirestore.getInstance()
        //replace with specific userID when login function is available
        db.collection("yong")
                .get()
                .addOnSuccessListener { docs ->
                    for (pet in docs) {
                        Log.d("aaa", pet.data.toString())
                        val id = pet.id
                        val name = pet.data["name"] as String
                        val type = pet.data["type"] as String
                        myPets.add(Pet(id = id, name = name, type = type))
                    }
                    adapter.submitList(myPets)
                }

        val addBtn = findViewById<View>(R.id.fab)
        addBtn.setOnClickListener {
            createPet()
        }
    }

    private fun selectPet(pet: Pet) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("petID", pet.id)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            data?.let {
                Log.d("aaa", "添加成功了啊")
                val id = data.getStringExtra("id")
                val name = data.getStringExtra("name")
                val type = data.getStringExtra("type")
                myPets.add(Pet(id = id!!, name = name!!, type = type!!))
                adapter.submitList(null)
                adapter.submitList(myPets)
            }
        }
    }

    private fun createPet() {
        val intent = Intent(this, AddActivity::class.java)
        startActivityForResult(intent, resultCode)
    }
}

class PetAdapter(private val onClick: (Pet) -> Unit) : ListAdapter<Pet, PetAdapter.PetViewHolder>(PetDiffCallback) {

    class PetViewHolder(itemView: View, val onClick: (Pet) -> Unit) :
            RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.pet_name)
        private var currentPet: Pet? = null

        init {
            itemView.setOnClickListener {
                currentPet?.let {
                    onClick(it)
                }
            }
        }

        fun bind(pet: Pet) {
            currentPet = pet
            nameText.text = pet.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pet_item, parent, false)
        return PetViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pet = getItem(position)
        holder.bind(pet)
    }
}

object PetDiffCallback : DiffUtil.ItemCallback<Pet>() {
    override fun areItemsTheSame(oldItem: Pet, newItem: Pet): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Pet, newItem: Pet): Boolean {
        return oldItem.id == newItem.id
    }
}