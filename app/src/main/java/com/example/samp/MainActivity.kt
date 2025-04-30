package com.example.samp

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TimePicker
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar
import com.example.samp.DBHelper

class MainActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    var selhour = 0
    var selminute = 0
    lateinit var dateText: EditText
    lateinit var addButton:Button
    lateinit var addItemText: EditText

    lateinit var adapter:CustomRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var reminderArrayList: MutableList<ReminderItem> =
            ArrayList()

        var day = 0
        var month = 0
        var year = 0
        var hour = 0
        var minute = 0

        reminderArrayList = getAllRows()

        val nextLayoutGo = findViewById<Button>(R.id.button)
        val reminderListView: RecyclerView = findViewById(R.id.recycleListView)
        reminderListView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)


        addItemText = findViewById<EditText>(R.id.inputText)
        addButton = findViewById<Button>(R.id.addToListButton)
        dateText = findViewById<EditText>(R.id.inputDate)


        nextLayoutGo.setOnClickListener {
            val intent = Intent(this, LayoutTwo::class.java)
            startActivity(intent)
        }

        dateText.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            hour = calendar.get(Calendar.HOUR)
            minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this@MainActivity, this@MainActivity, hour, minute,
                DateFormat.is24HourFormat(this))
            timePickerDialog.show()


        }

        addButton.setOnClickListener {
            val tempItem = ReminderItem()

            tempItem.itemText = addItemText.text.toString()
            tempItem.itemDate = dateText.text.toString()
            tempItem.rowNumber = reminderArrayList.size

            reminderArrayList.add(tempItem)
            addItemText.setText("")


            addButton.isEnabled = false

            adapter.notifyDataSetChanged()

            addToDatabase(tempItem)
            //reminderListView.adapter = adapter
            dateText.setText("")
        }

        adapter = CustomRecyclerAdapter(reminderArrayList as ArrayList<ReminderItem>)
        reminderListView.adapter = adapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT)
        {
            override fun onMove(
                p0: RecyclerView,
                p1: RecyclerView.ViewHolder,
                p2: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                val deletedItem: ReminderItem =
                    reminderArrayList.get(p0.adapterPosition)
                val position = p0.adapterPosition

                reminderArrayList.removeAt(p0.adapterPosition)
                adapter.notifyItemRemoved(p0.adapterPosition)

                Snackbar.make(reminderListView, "Deleted " + deletedItem.itemText, Snackbar.LENGTH_LONG)
                    .setAction(
                        "Undo",
                        View.OnClickListener {
                            reminderArrayList.add(position, deletedItem)
                            adapter.notifyItemInserted(position)

                        }).show()


                deletefromDatabase(position)
        }
        }).attachToRecyclerView(reminderListView)

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        selhour = hourOfDay
        selminute = minute
        val selecteddate:String = selhour.toString().plus(" : ").plus(selminute.toString())
        dateText.setText(selecteddate)



        if(!addItemText.text.equals(""))
            addButton.isEnabled = true
    }

    fun addToDatabase(item: ReminderItem){
        val db = DBHelper(this, null)

        val text = item.itemText
        val date = item.itemDate
        val switch = item.setReminder
        val rowNumber = item.rowNumber

        db.addRow(text, date, switch, rowNumber)

        db.close()
    }

    fun deletefromDatabase(rowNumber:Int){
        val db = DBHelper(this, null)

        db.deleteRow(rowNumber)

        db.close()
    }

    fun getAllRows(): MutableList<ReminderItem>{

        val rowlist: MutableList<ReminderItem> =
            ArrayList()
        val item = ReminderItem()

        val db = DBHelper(this, null)

        val cursor = db.getRow()

        if(cursor!!.moveToFirst()) {


            cursor!!.moveToFirst()


            item.itemText = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TEXT_COL))
            item.itemDate = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DATE_COl))
            item.setReminder = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.SWITCH_COL))
            item.rowNumber = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.ROW_COL))

            rowlist.add(item)

            while (cursor.moveToNext()) {

                val item = ReminderItem()

                item.itemText = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TEXT_COL))
                item.itemDate = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DATE_COl))
                item.setReminder = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.SWITCH_COL))
                item.rowNumber = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.ROW_COL))

                rowlist.add(item)
            }

            cursor.close()
        }

        return rowlist
    }


}