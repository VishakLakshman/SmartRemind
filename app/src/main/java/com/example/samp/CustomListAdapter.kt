package com.example.samp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class CustomListAdapter (private val context: Context, private val arrayList: java.util.ArrayList<ReminderItem>) : BaseAdapter()  {

    private lateinit var reminderListItem: TextView
    private lateinit var reminderListDate: TextView

    override fun getCount(): Int {
        return arrayList.size
    }
    override fun getItem(position: Int): Any {
        return position
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var converView = convertView
        converView = LayoutInflater.from(context).inflate(R.layout.listitem, parent, false)
        reminderListItem = converView.findViewById(R.id.listItemText)
        reminderListDate = converView.findViewById(R.id.listItemDate)

        reminderListItem.text = arrayList[position].itemText
        reminderListDate.text = arrayList[position].itemDate


        return converView
    }



}
