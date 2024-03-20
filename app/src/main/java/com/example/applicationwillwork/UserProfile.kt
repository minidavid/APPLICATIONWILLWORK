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

class UserProfile : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private lateinit var binding : ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //to edit email
        binding.btnNextAfterProfile.setOnClickListener{
            val intent = Intent(this, UserProfileEditEmailandPassword::class.java)
            startActivity(intent)
    }

        //back btn
        binding.btnNextAfterProfile.setOnClickListener{
            val intent = Intent(this, UserProfileEditEmailandPassword::class.java)
            startActivity(intent)
        }

        binding.btnBackUserProfile.setOnClickListener{
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
        }


        binding.btnShowNameProfile.setOnClickListener{
            val userName : String = binding.txtuserNameProfile.text.toString()

            if (userName.isNotEmpty()){
                readData(userName)
            }
            else{
                Toast.makeText(this,"Please enter userName",Toast.LENGTH_SHORT).show()
            }
        }



    }
    private fun readData(userName: String){
        database= FirebaseDatabase.getInstance().getReference("Users")
        database.child(userName).get().addOnSuccessListener {
            if (it.exists()){
                var userName = it.child("userName").value
                Toast.makeText(this,"Gotten user name",Toast.LENGTH_SHORT).show()
                binding.txtuserNameProfile.text = Editable.Factory.getInstance().newEditable(userName.toString())
            }
            else{
                Toast.makeText(this,"Enter userName",Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener{
            Toast.makeText(this,"Failed to get userName",Toast.LENGTH_SHORT).show()
        }
    }
}