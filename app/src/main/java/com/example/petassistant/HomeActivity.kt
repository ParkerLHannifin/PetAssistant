package com.example.petassistant

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {
    private val requestCode = 1
    private val myPets = mutableListOf<Pet>()
    private lateinit var adapter: PetAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val user = FirebaseAuth.getInstance().currentUser!!

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        adapter = PetAdapter { pet -> selectPet(pet) }
        recyclerView.adapter = adapter

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        db.collection(user.uid)
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
        intent.putExtra("name", pet.name)
        intent.putExtra("type", pet.type)
        startActivityForResult(intent, 4)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            data?.let {
                val id = data.getStringExtra("id")
                val name = data.getStringExtra("name")
                val type = data.getStringExtra("type")
                myPets.add(Pet(id = id!!, name = name!!, type = type!!))
                adapter.submitList(null)
                adapter.submitList(myPets)
            }
        }
        if (requestCode == 4 && resultCode == 4) { //for delete case
            data?.let {
                val id = data.getStringExtra("rm_id")
                adapter.submitList(null)
                myPets.removeAll { it.id == id }
                adapter.submitList(myPets)
            }
        }
    }

    private fun createPet() {
        val intent = Intent(this, AddActivity::class.java)
        startActivityForResult(intent, requestCode)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Notice")
                builder.setMessage("Are you sure to log out?")
                builder.setPositiveButton("Yes") { dialog, which ->
                    auth.signOut()
                    finish()
                }
                builder.setNeutralButton("Cancel") { _, _ -> }
                val dialog = builder.create()
                dialog.show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

class PetAdapter(private val onClick: (Pet) -> Unit) :
    ListAdapter<Pet, PetAdapter.PetViewHolder>(PetDiffCallback) {

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