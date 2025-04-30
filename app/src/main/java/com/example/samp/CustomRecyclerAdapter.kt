package com.example.samp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class CustomRecyclerAdapter (private val arrayList: java.util.ArrayList<ReminderItem>)
    : RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemText: TextView
        val itemDate: TextView


        init {
            // Define click listener for the ViewHolder's View
            itemText = view.findViewById(R.id.listItemText)
            itemDate = view.findViewById(R.id.listItemDate)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemText.text = arrayList[position].itemText
        holder.itemDate.text = arrayList[position].itemDate
    }

}