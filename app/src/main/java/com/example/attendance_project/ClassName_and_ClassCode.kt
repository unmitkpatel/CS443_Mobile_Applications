package com.example.attendance_project

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ClassName_and_ClassCode (var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ClassInformation.db"
        private const val TBL_CLASS =  "Class_Table"
        private const val CLASS_NAME = "Class_name"
        private const val CLASS_CODE = "Class_code"
        private val username_container: ArrayList<String> = ArrayList()
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createLoginTable = "CREATE TABLE " + TBL_CLASS + " (" +
                CLASS_NAME + " VARCHAR(256), " + CLASS_CODE + " INTEGER)"
        db?.execSQL(createLoginTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_CLASS")
        onCreate(db)
    }

    fun insertClassinfo(class_name: String, class_code: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CLASS_NAME, class_name)
        contentValues.put(CLASS_CODE, class_code)
        db.insert(TBL_CLASS, null, contentValues)
    }

    fun fetchClassData(): ArrayList<ClassInformation> {
        val db = this.readableDatabase
        val cursor: Cursor?
        cursor = db.rawQuery("SELECT * FROM $TBL_CLASS", null)
        val classInfo: ArrayList<ClassInformation> = ArrayList()
        while(cursor.moveToNext()){
            val classinfo: ClassInformation
            classinfo = ClassInformation()
            classinfo.class_name = cursor.getString(0)
            classinfo.class_code = cursor.getInt(1)
            classInfo.add(classinfo)
        }
        return classInfo
    }
    fun setUsername(username: String) {
        username_container.add(username)
    }

    fun getUsername(): ArrayList<String> {
        return username_container
    }
}