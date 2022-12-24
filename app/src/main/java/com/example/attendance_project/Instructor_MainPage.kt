package com.example.attendance_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class Instructor_MainPage : AppCompatActivity() {
    lateinit var linearLayout: LinearLayout
    lateinit var editText: EditText
    lateinit var class_name: String
    private var class_code: Int = 0
    val classcodelist: ArrayList<Int> = ArrayList()
    val classnamelist: ArrayList<String> = ArrayList()
    lateinit var username: String
    lateinit var text: TextView
    val instructor_info: SQL_Content_for_Instructor = SQL_Content_for_Instructor(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_main_page)

        this.linearLayout = findViewById(R.id.linearLayout)
        showClassInputBox()

    }
    private fun lectureCode(): Int {
        val lecture_code = (100000..999999).random()
        return lecture_code
    }

    private fun secondMessageForAttendance() {
        val build = AlertDialog.Builder(this)
        with (build) {
            setMessage("I will do it later")
            show()
        }
    }

    private fun secondMessageForCode(code: Int) {
        var lecture_code = lectureCode()
        val build = AlertDialog.Builder(this)
        with (build) {
            setMessage("Today's lecture code is:\n" + lecture_code)
            show()
        }
        var classinfo: ClassCode_and_LectureCode = ClassCode_and_LectureCode(this)
        classinfo.updateClassData(code, lecture_code)
    }

    fun classnamelist(): ArrayList<String> {
        classnamelist.add(class_name)
        return classnamelist
    }

    fun setusername(name: String): String {
        username = name
        return username
    }

    fun classcodelist(): ArrayList<Int> {
        classcodelist.add(class_code)
        return classcodelist
    }


    private fun createAndAddView(message: String) {
        var random_code = (100000..999999).random()
        class_code = random_code
        val button = Button(this)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        button.layoutParams = params
        button.text = message + "\n Class code: " + class_code
        linearLayout.addView(button)
        var check: SQL_Content_for_Instructor = SQL_Content_for_Instructor(this)
        val index = check.getUsername().size - 1
        check.insertInstructor(check.getUsername()[index], class_name, class_code)
        var classinfo: ClassName_and_ClassCode = ClassName_and_ClassCode(this)
        classinfo.insertClassinfo(class_name, class_code)
        var lectureinfo: ClassCode_and_LectureCode = ClassCode_and_LectureCode(this)
        lectureinfo.insertClassinfo(class_code, 0)
        Log.d("Test", "Name: " + check.getUsername().get(index) + " Class: " + class_name + " Code: " + random_code )
        button.setOnClickListener{
            val build = AlertDialog.Builder(this)
            val inflater = LayoutInflater.from(this)
            //val dialogLayout = inflater.inflate(R.layout.layout_for_dialog,null)
            //val textView = dialogLayout.findViewById<EditText>(R.id.textView9)
            with(build) {
                setTitle("Create Code or Check Attendance")
                setPositiveButton("Attendance") {dialog, which ->
                    secondMessageForAttendance()
                }
                setNeutralButton("Code") {dialog, which ->
                    secondMessageForCode(class_code)

                }
                setNegativeButton("Cancel") {dialog, which ->
                    Log.d("Main", "Negative button clicked")
                }
                //setView(dialogLayout)
                show()
            }
        }

    }


    private fun showClassInputBox() {
        val add_class = findViewById<Button>(R.id.button7)
        add_class.setOnClickListener{
            val build = AlertDialog.Builder(this)
            val inflater = LayoutInflater.from(this)
            val dialogLayout = inflater.inflate(R.layout.layout_for_dialog,null)
            this.editText = dialogLayout.findViewById<EditText>(R.id.textView8)
            with(build) {
                setTitle("Add Class!")
                setPositiveButton("Insert") {dialog, which ->
                    class_name = editText.text.toString()
                    //classnamelist()
                    createAndAddView(editText.text.toString())
                }
                setNegativeButton("Cancel") {dialog, which ->
                    Log.d("Main", "Negative button clicked")
                }
                setView(dialogLayout)
                show()
            }
        }
    }

}