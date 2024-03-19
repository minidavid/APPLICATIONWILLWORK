package com.example.applicationwillwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.applicationwillwork.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

import com.example.applicationwillwork.Models.MessageClass

import com.google.firebase.database.ChildEventListener

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.FirebaseDatabase


class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // Switch to registration activity
        binding.btnSignUpLogin.setOnClickListener {
            switchActivityToRegister()
         }

        // Log in user
        binding.btnLoginLogin.setOnClickListener {
            val email = binding.txtEmailLogin.text.toString()
            val password = binding.txtPasswordLogin.text.toString()



            // Check for empty fields
            if (email.isNotEmpty() && password.isNotEmpty() && isValidEmail(email)) {
                // Log in user
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Login successful, switch to MainActivity
                            val intent = Intent(this, moreInformation::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Login failed, display error message
                            val errorMessage = task.exception?.message ?: "Authentication failed."
                            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                // Fields are empty, display error message
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    // Switch to registration activity
    private fun switchActivityToRegister() {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()

        // Check if user is already logged in
        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, moreInformation::class.java)
            startActivity(intent)
            finish()
        }
    }
}
