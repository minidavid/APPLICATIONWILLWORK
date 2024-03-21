package com.example.applicationwillwork

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.imageview.ShapeableImageView

class MyAdapter (private val articlesList : ArrayList<Articles>):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return articlesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = articlesList[position]
        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.txtHeading.text = currentItem.heading
    }

    class MyViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)
    {
        val titleImage:ShapeableImageView = itemView.findViewById(R.id.titleImage)
        val txtHeading:TextView = itemView.findViewById(R.id.txtHeading)
    }

}