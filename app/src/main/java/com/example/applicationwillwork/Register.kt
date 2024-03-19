package com.example.applicationwillwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.applicationwillwork.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

        binding.btnLogin.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        binding.btnSignUp.setOnClickListener{
            val firstName = binding.txtFirstName.text.toString()
            val lastName = binding.txtLastName.text.toString()
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPassword.text.toString()
            val confirmPass = binding.txtConfirmPassword.text.toString()

            val age = 0 // You might want to change this to a valid age input field

            val nameTogether = "$firstName $lastName"
            val userName = nameTogether

            val user = User(userName, firstName, lastName, age, email)

            if (email.isNotEmpty() && firstName.isNotEmpty() && password.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (password == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                database.child(firstName).setValue(user).addOnSuccessListener {
                                    binding.txtFirstName.text.clear()
                                    binding.txtLastName.text.clear()
                                    // Other fields to clear if necessary
                                }
                                val intent = Intent(this, Login::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "The Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields exist", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

data class User(val userName: String? = null, val firstName: String? = null, val lastName: String? = null, val age: Int = 0, val email: String? = null)
