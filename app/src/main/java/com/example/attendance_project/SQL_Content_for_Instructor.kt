package com.example.attendance_project

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQL_Content_for_Instructor (var context: Context) :SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "Instructor.db"
        private const val TBL_INSTRUCTOR = "Instructor_Table"
        private const val USER_NAME = "User_Name"
        private const val CLASS_NAME = "Class_Name"
        private const val CLASS_CODE = "Class_Code"
        private val username_container: ArrayList<String> = ArrayList()
        private val password_container: ArrayList<String> = ArrayList()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createInstructorTable = "CREATE TABLE " + TBL_INSTRUCTOR + " (" +
                USER_NAME + " VARCHAR(256), " + CLASS_NAME + " VARCHAR(256), " +
                CLASS_CODE + " INTEGER)"
        db?.execSQL(createInstructorTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TBL_INSTRUCTOR)
        onCreate(db)
    }

    fun insertInstructor(user_name: String, class_name: String, class_code: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_NAME, user_name)
        contentValues.put(CLASS_NAME, class_name)
        contentValues.put(CLASS_CODE, class_code)
        db.insert(TBL_INSTRUCTOR, null, contentValues)
    }

    fun fetchInstructorData(): ArrayList<InstructorModel> {
        val db = this.readableDatabase
        val cursor: Cursor?
        cursor = db.rawQuery("SELECT * FROM $TBL_INSTRUCTOR", null)
        val instructorInfo: ArrayList<InstructorModel> = ArrayList()
        while(cursor.moveToNext()){
            val inst: InstructorModel
            inst = InstructorModel()
            inst.user_name = cursor.getString(0)
            inst. class_name = cursor.getString(1)
            inst.class_code = cursor.getInt(2)
            instructorInfo.add(inst)
        }
        return instructorInfo
    }

    fun setUsername(username: String) {
        username_container.add(username)
    }

    fun getUsername(): ArrayList<String> {
        return username_container
    }
}
