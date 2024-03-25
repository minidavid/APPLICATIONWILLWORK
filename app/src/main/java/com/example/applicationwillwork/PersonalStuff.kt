package com.example.applicationwillwork

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationwillwork.Models.UserData
import com.example.applicationwillwork.view.UserAdapterJournal
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PersonalStuff : AppCompatActivity() {
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recy: RecyclerView
    private lateinit var userList: ArrayList<UserData>
    private lateinit var userAdapterJournal: UserAdapterJournal

    private lateinit var database: DatabaseReference
    private lateinit var journalEntriesRef: DatabaseReference
    private lateinit var journalEntriesListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_stuff)

        // Initialize Firebase database reference
        database = FirebaseDatabase.getInstance().reference

        // Initialize RecyclerView and adapter
        userList = ArrayList()
        userAdapterJournal = UserAdapterJournal(this, userList)
        recy = findViewById(R.id.mRecycler)
        recy.layoutManager = LinearLayoutManager(this)
        recy.adapter = userAdapterJournal

        // Load data from Firebase
        loadDataFromFirebase()

        // Set onClickListener for adding button
        addsBtn = findViewById(R.id.addingBtn)
        addsBtn.setOnClickListener {
            addInfo()
        }
    }

    private fun loadDataFromFirebase() {
        // Reference to "journalEntries" node
        journalEntriesRef = FirebaseDatabase.getInstance().getReference("journalEntries")
        journalEntriesListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (dataSnapshot in snapshot.children) {
                    val title = dataSnapshot.child("title").getValue(String::class.java)
                    val content = dataSnapshot.child("content").getValue(String::class.java)
                    if (title != null && content != null) {
                        userList.add(UserData(title, content))
                    }
                }
                userAdapterJournal.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Toast.makeText(this@PersonalStuff, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
            }
        }
        // Add ValueEventListener to journalEntriesRef
        journalEntriesRef.addValueEventListener(journalEntriesListener)
    }

    private fun addInfo() {
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.add_item_journal, null)
        val addDialog = AlertDialog.Builder(this)

        // Set view
        val txtNewTitleJournal = v.findViewById<EditText>(R.id.txtNewTitleJournal)
        val txtNewSubTitleJournal = v.findViewById<EditText>(R.id.txtNewSubTitleJournal)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok") { dialog, _ ->
            val titles = txtNewTitleJournal.text.toString()
            val journalContent = txtNewSubTitleJournal.text.toString()

            userList.add(UserData(titles, journalContent))
            userAdapterJournal.notifyDataSetChanged()
            Toast.makeText(this, "Adding journal text", Toast.LENGTH_SHORT).show()
            dialog.dismiss()

            // Create a new UserData object
            val userData = UserData(titles, journalContent)

            // Generate a unique key for the entry in the "journalEntries" node
            val entryKey = database.child("journalEntries").push().key

            // Check if the key is generated successfully
            if (entryKey != null) {
                // Store the journal entry under the "journalEntries" node with the generated key
                database.child("journalEntries").child(entryKey).setValue(userData)
                    .addOnSuccessListener {
                        // Data successfully sent to the database
                        Toast.makeText(this, "Journal entry added successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        // Error occurred while sending data to the database
                        Toast.makeText(this, "Failed to add journal entry", Toast.LENGTH_SHORT).show()
                    }
            } else {
                // Handle the case when the key is not generated
                Toast.makeText(this, "Failed to generate entry key", Toast.LENGTH_SHORT).show()
            }

            // Clear the EditText fields after adding the entry
            txtNewTitleJournal.text.clear()
            txtNewSubTitleJournal.text.clear()
        }

        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
        }

        addDialog.create().show()
    }
}
