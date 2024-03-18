package com.example.applicationwillwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.applicationwillwork.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        //switch pages
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

            //check for validity of email password, etc
            if (email.isNotEmpty() && firstName.isNotEmpty() && password.isNotEmpty() && confirmPass.isNotEmpty())
            {
                //if confirm password is the same as main password
                if (password == confirmPass)
                {
                    //add user and password
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener()
                    {

                        if (it.isSuccessful)
                        {
                            val intent = Intent(this,Login::class.java)
                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }

                }
                else
                {
                    Toast.makeText(this,"The Password is not matching",Toast.LENGTH_SHORT).show()
                }

            }
            else
            {
                Toast.makeText(this,"Empty Fields exist",Toast.LENGTH_SHORT).show()
            }

            //if statement

        }

    }

    //-----------function to switch to login activity-------------------
    private fun switchActivityToLogin()
    {
        //press login button top left
        val btnLogin:Button = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener{
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
        //----------------------------------------------------------


    }
}
