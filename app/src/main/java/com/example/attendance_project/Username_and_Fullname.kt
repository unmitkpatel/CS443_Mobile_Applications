package com.example.attendance_project

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Username_and_Fullname (var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "Student_Full_Name.db"
        private const val TBL_STUDENT_NAME =  "Student_NAME_Table"
        private const val USER_NAME = "Username"
        private const val CLASS_CODE = "Classcode"
        private const val FULL_NAME = "Fullname"
        private val username_container: ArrayList<String> = ArrayList()
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createLoginTable = "CREATE TABLE " + TBL_STUDENT_NAME + " (" +
                USER_NAME + " VARCHAR(256), " + CLASS_CODE + " INTEGER, " + FULL_NAME + " VARCHAR(256))"
        db?.execSQL(createLoginTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_STUDENT_NAME")
        onCreate(db)
    }

    fun insertCredentials(username: String, class_code: Int, name: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_NAME, username)
        contentValues.put(CLASS_CODE, class_code)
        contentValues.put(FULL_NAME, name)
        db.insert(TBL_STUDENT_NAME, null, contentValues)
    }

    fun fetchCredentialData(): ArrayList<StudentNameInformation> {
        val db = this.readableDatabase
        val cursor: Cursor?
        cursor = db.rawQuery("SELECT * FROM $TBL_STUDENT_NAME", null)
        val nameinfo: ArrayList<StudentNameInformation> = ArrayList()
        while(cursor.moveToNext()){
            val cred: StudentNameInformation
            cred = StudentNameInformation()
            cred.Username = cursor.getString(0)
            cred.Classcode = cursor.getInt(1)
            cred.FullName = cursor.getString(2)
            nameinfo.add(cred)
        }
        return nameinfo
    }

    fun setUsername(username: String) {
        username_container.add(username)
    }

    fun getUsername(): ArrayList<String> {
        return username_container
    }
}