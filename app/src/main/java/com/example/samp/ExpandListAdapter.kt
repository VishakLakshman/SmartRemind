package com.example.samp

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import java.util.HashMap

class ExpandListAdapter internal constructor(
    private val context:Context,
    private val titleList: List<String>,
    private val dataList: HashMap<String, List<String>>)
     : BaseExpandableListAdapter() {
    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return this.dataList[this.titleList[groupPosition]]!![childPosition]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return this.dataList[this.titleList[listPosition]]!!.size
    }
    override fun getGroup(listPosition: Int): Any {
        return this.titleList[listPosition]
    }
    override fun getGroupCount(): Int {
        return this.titleList.size
    }
    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }
    override fun hasStableIds(): Boolean {
        return false
    }
    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }

    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var converView = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        if (converView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            converView = layoutInflater.inflate(R.layout.listitem, parent, false)
        }
        val expandedListTextView = converView!!.findViewById<TextView>(R.id.listItemText)
        expandedListTextView.text = expandedListText
        return converView
    }

    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var converView = convertView
        val listTitle = getGroup(listPosition) as String
        if (converView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            converView = layoutInflater.inflate(R.layout.listitem, parent, false)
        }
        val listTitleTextView = converView!!.findViewById<TextView>(R.id.listItemText)
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = listTitle
        return converView
    }


}
