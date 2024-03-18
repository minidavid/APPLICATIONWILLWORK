package com.example.applicationwillwork

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.text.method.LinkMovementMethod // Corrected import
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class moreInformation : AppCompatActivity() {

    private lateinit var age: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var buttonNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_information)

        hyperLinksPrivacyPolicyandTermsOfService()

        checkBox = findViewById(R.id.chkPrivacyPolicy)
        age = findViewById(R.id.txtTextDate)
        buttonNext = findViewById(R.id.btnNext)

        // Add a TextChangedListener to the age EditText
        age.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No implementation needed
            }

            override fun afterTextChanged(s: Editable?) {
                // Check if age EditText has text
                val isAgeEmpty = s.isNullOrEmpty()

                // Enable/disable button based on age EditText content and checkbox status
                buttonNext.isEnabled = !isAgeEmpty && checkBox.isChecked
            }
        })

        // Add OnClickListener to the checkBox
        checkBox.setOnClickListener {
            // Enable/disable button based on age EditText content and checkbox status
            buttonNext.isEnabled = !age.text.isNullOrEmpty() && checkBox.isChecked
        }

        // Set OnClickListener for the Next button
        buttonNext.setOnClickListener {
            // Retrieve age from EditText
            val ageText = age.text.toString()

            // Display toast if age is empty
            if (ageText.isEmpty()) {
                Toast.makeText(this@moreInformation, "Please enter your age", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Move to next page if conditions are met
            val ageValue = ageText.toInt()
            if (ageValue > 13 && checkBox.isChecked) {
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this@moreInformation, "You must be at least 14 years old and agree to the privacy policy", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hyperLinksPrivacyPolicyandTermsOfService() {
        //link
        val textView: TextView = findViewById(R.id.txtTermsofService)
        textView.movementMethod = LinkMovementMethod.getInstance()

        val textView2: TextView = findViewById(R.id.txtPrivacyPolicy)
        textView2.movementMethod = LinkMovementMethod.getInstance()
    }
}
