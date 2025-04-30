package com.example.samp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {

        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + TEXT_COL + " TEXT, " +
                DATE_COl + " TEXT," +
                SWITCH_COL + " INTEGER," +
                ROW_COL + " INTEGER" +")")

        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addRow(text : String, date : String, switch:Int, row:Int ){

        val values = ContentValues()

        values.put(TEXT_COL, text)
        values.put(DATE_COl, date)
        values.put(SWITCH_COL, switch)
        values.put(ROW_COL, row)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    fun deleteRow(row:Int)
    {
        val db = this.writableDatabase

        db.delete(TABLE_NAME, "row=?",arrayOf(row.toString()))

        db.execSQL("UPDATE "+ TABLE_NAME+" SET row = (row -1) WHERE row > "+row)

        db.close()

    }

    fun getRow(): Cursor? {

        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }



    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "reminderapp"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "reminderlist"

        val TEXT_COL = "text"

        val DATE_COl = "date"

        val SWITCH_COL = "switch"

        val ROW_COL = "row"
    }

}