package com.example.applicationwillwork;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Call extends AppCompatActivity {

    private EditText txtPhoneNumber;
    private Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        // Initialize views
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        btnCall = findViewById(R.id.btnCall);

        // Set onClickListener for btnCall
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoneNumber();
            }
        });
    }

    // Method to initiate phone call
    private void callPhoneNumber() {
        String phoneNumber = txtPhoneNumber.getText().toString().trim();

        // Check if phone number is not empty
        if (!phoneNumber.isEmpty()) {
            // Create intent to initiate phone call
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        } else {
            // Display a toast message indicating that the phone number is empty
            Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
        }
    }
}
