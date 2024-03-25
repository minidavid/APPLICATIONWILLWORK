package com.example.applicationwillwork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersontoPersonChat extends AppCompatActivity {

    ListView lv;
    ImageButton btnBackChat;
    EditText ed;
    Button btnSend;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ArrayAdapter<String> adapter;
    ArrayList<String> al;

    List<String> badWords = Arrays.asList("idiot", "crap", "dumb", "stupid","crap","shit","damn","goddamn","autistic","fuck","shit"); // Add your bad words here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personto_person_chat);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        al = new ArrayList<>();

        btnBackChat = findViewById(R.id.btnBackChatPtoP);
        ed = findViewById(R.id.edmsgPtoP);
        lv = findViewById(R.id.lvPtoP);
        btnSend = findViewById(R.id.btnSendPtoP);

        btnBackChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersontoPersonChat.this, Home.class);
                startActivity(intent);
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(adapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser != null) {
                    String uid = currentUser.getUid();


                    mDatabase.child("Users").child(uid).child("userName").get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            if (dataSnapshot.exists()) {
                                String userName = dataSnapshot.getValue(String.class);
                                Toast.makeText(PersontoPersonChat.this, "Got user name: " + userName, Toast.LENGTH_SHORT).show();

                                String finalUserName = userName;
                                String message = finalUserName + ": " + ed.getText().toString().trim();

                                if (!containsBadWord(message)) {
                                    if (!message.isEmpty()) {
                                        String key = mDatabase.child("MessagesPtoP").push().getKey();
                                        mDatabase.child("MessagesPtoP").child(key).setValue(message)
                                                .addOnCompleteListener(task1 -> {
                                                    if (task1.isSuccessful()) {
                                                        Toast.makeText(PersontoPersonChat.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(PersontoPersonChat.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                        ed.setText("");
                                    }
                                } else {
                                    Toast.makeText(PersontoPersonChat.this, "Message contains forbidden words", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(PersontoPersonChat.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PersontoPersonChat.this, "Failed to get user name: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(PersontoPersonChat.this, "User not signed in", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mDatabase.child("MessagesPtoP").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String message = snapshot.getValue(String.class);
                al.add(message);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private boolean containsBadWord(String message) {
        for (String word : badWords) {
            if (message.toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
