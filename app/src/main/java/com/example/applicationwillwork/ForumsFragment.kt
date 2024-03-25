package com.example.applicationwillwork

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.applicationwillwork.MainActivity
import com.example.applicationwillwork.R

class ForumsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the button by its ID
        val btnToGroupChat: Button = view.findViewById(R.id.btnToChat)

        // Set OnClickListener for btnToGroupChat
        btnToGroupChat.setOnClickListener {
            // Start MainActivity
            val intent = Intent(activity, GroupChat::class.java)
            startActivity(intent)
        }


        //
        val btnCall: Button = view.findViewById(R.id.btnCall)

        btnCall.setOnClickListener {
            // Start MainActivity
            val intent = Intent(activity, Call::class.java)
            startActivity(intent)
        }

        //
        val btnToChat: Button = view.findViewById(R.id.btnToChat)

        btnToChat.setOnClickListener {
            // Start MainActivity
            val intent = Intent(activity, PersontoPersonChat::class.java)
            startActivity(intent)
        }
    }
}
