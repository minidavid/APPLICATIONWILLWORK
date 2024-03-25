package com.example.applicationwillwork

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.text.Editable
import android.widget.Toast
import com.example.applicationwillwork.databinding.ActivityRegisterBinding
import com.example.applicationwillwork.databinding.ActivityUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class UserProfile : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private lateinit var binding : ActivityUserProfileBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)

        binding.btnBackUserProfile.setOnClickListener{
            val intent = Intent(this,Home::class.java)
            startActivity(intent)
        }


        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnNextAfterProfile.setOnClickListener {
            val intent = Intent(this, UserProfileEditEmailandPassword::class.java)
            startActivity(intent)
        }


        binding.btnShowNameProfile.setOnClickListener{
            val userName : String = binding.txtUserNameProfile.text.toString()

            if (userName.isNotEmpty()){
                readData(userName)
            }
            else{
                Toast.makeText(this,"Please enter userName",Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun readData(userName: String) {
        database = FirebaseDatabase.getInstance().getReference("Users")
        val currentUser = firebaseAuth.currentUser
        val uid = currentUser?.uid ?: ""

        database.child(uid).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val userName = dataSnapshot.child("userName").value
                Toast.makeText(this, "Got user name: $userName", Toast.LENGTH_SHORT).show()
                binding.txtUserNameProfile.text = userName.toString()
            } else {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                binding.txtUserNameProfile.text = "no name"
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Failed to get user name: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }
}