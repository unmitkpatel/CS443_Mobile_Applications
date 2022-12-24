package com.example.attendance_project

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Instructors_Credentials (var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Login_Info.db"
        private const val TBL_INSTRUCTOR_LOGIN =  "Instructor_Login_Table"
        private const val USER_NAME = "Username"
        private const val PASSWORD = "Password"
        private val username_container: ArrayList<String> = ArrayList()
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createLoginTable = "CREATE TABLE " + TBL_INSTRUCTOR_LOGIN + " (" +
                USER_NAME + " VARCHAR(256), " + PASSWORD + " VARCHAR(256))"
        db?.execSQL(createLoginTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_INSTRUCTOR_LOGIN")

        onCreate(db)
    }

    fun insertCredentials(username: String, password: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_NAME, username)
        contentValues.put(PASSWORD, password)
        db.insert(TBL_INSTRUCTOR_LOGIN, null, contentValues)
    }

    fun fetchCredentialData(): ArrayList<Credentials> {
        val db = this.readableDatabase
        val cursor: Cursor?
        cursor = db.rawQuery("SELECT * FROM $TBL_INSTRUCTOR_LOGIN", null)
        val loginInfo: ArrayList<Credentials> = ArrayList()
        while(cursor.moveToNext()){
            val cred: Credentials
            cred = Credentials()
            cred.Username = cursor.getString(0)
            cred.Password = cursor.getString(1)
            loginInfo.add(cred)
        }
        return loginInfo
    }
    fun setUsername(username: String) {
        username_container.add(username)
    }

    fun getUsername(): ArrayList<String> {
        return username_container
    }
}