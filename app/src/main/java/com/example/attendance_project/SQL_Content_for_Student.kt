package com.example.attendance_project

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQL_Content_for_Student (var context: Context) :SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "Student.db"
        private const val TBL_STUDENT = "Student_Table"
        private const val USER_NAME = "User_Name"
        private const val CLASS_NAME = "Class_Name"
        private const val CLASS_CODE = "Class_Code"
        private val username_container: ArrayList<String> = ArrayList()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createInstructorTable =
            "CREATE TABLE $TBL_STUDENT ($USER_NAME VARCHAR(256), $CLASS_NAME VARCHAR(256), $CLASS_CODE INTEGER)"
        db?.execSQL(createInstructorTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_STUDENT")
        onCreate(db)
    }

    fun insertStudent(user_name: String, class_name: String, class_code: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_NAME, user_name)
        contentValues.put(CLASS_NAME, class_name)
        contentValues.put(CLASS_CODE, class_code)
        db.insert(TBL_STUDENT, null, contentValues)
    }

    fun fetchStudentData(): ArrayList<StudentModel> {
        val db = this.readableDatabase
        val cursor: Cursor?
        cursor = db.rawQuery("SELECT * FROM $TBL_STUDENT", null)
        val studentInfo: ArrayList<StudentModel> = ArrayList()
        while(cursor.moveToNext()){
            val stud: StudentModel
            stud = StudentModel()
            stud.user_name = cursor.getString(0)
            stud. class_name = cursor.getString(1)
            stud.class_code = cursor.getInt(2)
            studentInfo.add(stud)
        }
        return studentInfo
    }

    fun setUsername(username: String) {
        username_container.add(username)
    }

    fun getUsername(): ArrayList<String> {
        return username_container
    }
}
