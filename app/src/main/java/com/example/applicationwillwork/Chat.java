package com.example.applicationwillwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.applicationwillwork.Models.MessageClass;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Chat extends AppCompatActivity {

    EditText editTextMessage;
    Button buttonSend;
    TextView textViewConversation;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        textViewConversation = findViewById(R.id.textViewConversation);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("messages");

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        // Listen for new messages
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @NonNull String s) {
                MessageClass message = dataSnapshot.getValue(MessageClass.class);
                if (message != null) {
                    appendMessageToConversation(message.getSender() + ": " + message.getMessage());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @NonNull String s) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @NonNull String s) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void sendMessage() {
        String messageText = editTextMessage.getText().toString().trim();
        if (!TextUtils.isEmpty(messageText)) {
            String id = databaseReference.push().getKey();
            if (id != null) {
                MessageClass message = new MessageClass("User", messageText); // Assume sender is "User"
                databaseReference.child(id).setValue(message);
                editTextMessage.setText("");
            }
        }
    }

    private void appendMessageToConversation(String message) {
        String currentConversation = textViewConversation.getText().toString();
        currentConversation += "\n" + message;
        textViewConversation.setText(currentConversation);
    }
}
