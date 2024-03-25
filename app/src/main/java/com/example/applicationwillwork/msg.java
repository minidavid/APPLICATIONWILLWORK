package com.example.applicationwillwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class msg extends AppCompatActivity {

    ListView lv;
    ImageButton btnBackChat;
    EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        ArrayList<String> al = new ArrayList<>();

        btnBackChat = findViewById(R.id.btnBackChat);
        btnBackChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(msg.this, Home.class);
                startActivity(intent);
            }
        });

        ed = findViewById(R.id.edmsg);
        lv = findViewById(R.id.lv);

        db.getReference("Messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Toast.makeText(msg.this, "Message Added " + snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                al.add(snapshot.getValue().toString());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(msg.this, android.R.layout.simple_list_item_1, al);
                lv.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth a = FirebaseAuth.getInstance();
                Date currentDate = Calendar.getInstance().getTime();
                db.getReference("Messages").child(a.getUid() + currentDate.toString()).setValue(ed.getText().toString());
                ed.setText("");
            }
        });
    }
}
