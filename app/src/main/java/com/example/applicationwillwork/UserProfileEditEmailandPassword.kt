package com.example.applicationwillwork

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class UserProfileEditEmailandPassword : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var currentUserEmail: String
    private lateinit var txtEmailProfile: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile_edit_email_and_password)

        firebaseAuth = FirebaseAuth.getInstance()

        // Initialize views
        txtEmailProfile = findViewById(R.id.txtEmailProfile)

        // Get current user email
        val currentUser = firebaseAuth.currentUser
        currentUserEmail = currentUser?.email ?: ""

        // Display current email in TextView
        txtEmailProfile.text = currentUserEmail

        // Initialize logout button
        val btnLogoutProfile = findViewById<Button>(R.id.btnLogoutProfile)
        btnLogoutProfile.setOnClickListener {
            logout()
        }

        // Initialize back button
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)
            finish() // Finish current activity to prevent going back to it when pressing back
        }
    }

    // Function to log out the user
    private fun logout() {
        firebaseAuth.signOut()
        // Redirect to the login activity
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish() // Close current activity to prevent going back to the profile after logout
    }
}
