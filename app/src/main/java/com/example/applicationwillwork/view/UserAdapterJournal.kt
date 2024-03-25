package com.example.applicationwillwork.view

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationwillwork.Models.UserData
import com.example.applicationwillwork.R
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import org.w3c.dom.Text

class UserAdapterJournal(val c: Context, val userList: ArrayList<UserData>) :
    RecyclerView.Adapter<UserAdapterJournal.UserViewHolder>() {

    inner class UserViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        val titleTextView: TextView = v.findViewById(R.id.txtTitleJournal)
        val contentTextView: TextView = v.findViewById(R.id.txtSubTitleJournal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item2, parent, false)
        return UserViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userData = userList[position]
        holder.titleTextView.text = userData.title
        holder.contentTextView.text = userData.content
    }
}
