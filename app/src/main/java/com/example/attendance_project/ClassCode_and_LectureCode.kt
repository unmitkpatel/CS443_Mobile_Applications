package com.example.attendance_project

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ClassCode_and_LectureCode (var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Lecture_Info.db"
        private const val TBL_LECTURE =  "Lecture_Table"
        private const val CLASS_CODE = "Class_code"
        private const val LECTURE_CODE = "Lecture_code"
        private val username_container: ArrayList<String> = ArrayList()
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createLoginTable =
            "CREATE TABLE $TBL_LECTURE ($CLASS_CODE INTEGER, $LECTURE_CODE INTEGER)"
        db?.execSQL(createLoginTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_LECTURE")
        onCreate(db)
    }

    fun insertClassinfo(class_code: Int, lecture_code: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CLASS_CODE, class_code)
        contentValues.put(LECTURE_CODE, lecture_code)
        db.insert(TBL_LECTURE, null, contentValues)
    }

    fun fetchClassData(): ArrayList<LectureInformation> {
        val db = this.readableDatabase
        val cursor: Cursor?
        cursor = db.rawQuery("SELECT * FROM $TBL_LECTURE", null)
        val lectureInfo: ArrayList<LectureInformation> = ArrayList()
        while(cursor.moveToNext()){
            val lectureinfo: LectureInformation
            lectureinfo = LectureInformation()
            lectureinfo.class_code = cursor.getInt(0)
            lectureinfo.lecture_code = cursor.getInt(1)
            lectureInfo.add(lectureinfo)
        }
        return lectureInfo
    }
    fun setUsername(username: String) {
        username_container.add(username)
    }

    fun getUsername(): ArrayList<String> {
        return username_container
    }

    fun updateClassData(class_code: Int, lecture_code: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(LECTURE_CODE, lecture_code)
        db.update(TBL_LECTURE, contentValues, CLASS_CODE + " = " + class_code, null)
    }
}