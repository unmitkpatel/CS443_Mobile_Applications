package com.example.attendance_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Student_MainPage : AppCompatActivity() {
    lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_main_page)
        linearLayout = findViewById(R.id.linearLayout2)
        showClassInputBox()
    }

    private fun createAndAddView(code: Int) {
        val check: ClassName_and_ClassCode = ClassName_and_ClassCode(this)
        val arr: ArrayList<ClassInformation> = check.fetchClassData()
        Log.d("Check", "Works: " + arr.size)
        for (i in arr) {
            Log.d("Next", "Code: " + i.class_code + "    EditCode: " + code)
            if (i.class_code == code) {
                val button = Button(this)
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                button.layoutParams = params
                button.text = i.class_name + "\n Class code: " + i.class_code
                this.linearLayout.addView(button)
                val insert_info: SQL_Content_for_Student = SQL_Content_for_Student(this)
                val method: SQL_Content_for_Student = SQL_Content_for_Student(this)
                insert_info.insertStudent(method.getUsername().get(method.getUsername().size - 1), i.class_name, i.class_code)
                button.setOnClickListener {
                    val build = AlertDialog.Builder(this)
                    val inflater = LayoutInflater.from(this)
                    val dialogLayout = inflater.inflate(R.layout.layout_for_dialog_student,null)
                    val editText = dialogLayout.findViewById<EditText>(R.id.editTextNumber)
                    with(build) {
                        setTitle("Enter today's code")
                        setPositiveButton("Submit") {dialog, which ->
                            val test = Integer.parseInt(editText.text.toString())
                            afterCodeMessage(i.class_code, test)
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
    }

    private fun showClassInputBox() {
        val add_class = findViewById<Button>(R.id.button7)
        add_class.setOnClickListener{
            val build = AlertDialog.Builder(this)
            val inflater = LayoutInflater.from(this)
            val dialogLayout = inflater.inflate(R.layout.layout_for_dialog_student,null)
            val editText = dialogLayout.findViewById<EditText>(R.id.editTextNumber)
            with(build) {
                setTitle("Add Class!")
                setPositiveButton("Insert") {dialog, which ->
                    val test = Integer.parseInt(editText.text.toString())
                    createAndAddView(test)
                    Log.d("Check", "Test Value: " + test)
                }
                setNegativeButton("Cancel") {dialog, which ->
                    Log.d("Main", "Negative button clicked")
                }
                setView(dialogLayout)
                show()
            }
        }
    }

    private fun afterCodeMessage(class_code: Int, lecture_code: Int) {
        var context = this
        val build = AlertDialog.Builder(this)
        with (build) {
            var check = false
            var lecCode = ClassCode_and_LectureCode(context)
            var arr = lecCode.fetchClassData()
            for (i in arr) {
                if (i.class_code == class_code) {
                    if (i.lecture_code == lecture_code) {
                        check = true
                        break
                    }
                }
            }
            if (check) {
                val build = AlertDialog.Builder(context)
                val inflater = LayoutInflater.from(context)
                val dialogLayout = inflater.inflate(R.layout.activity_student_name,null)
                val editText = dialogLayout.findViewById<EditText>(R.id.editTextTextPersonName3)
                with(build) {
                    setTitle("Name")
                    setPositiveButton("Submit") {dialog, which ->
                        var full_name = Username_and_Fullname(context)
                        var uname = full_name.getUsername().get(full_name.getUsername().size - 1)
                        full_name.insertCredentials(uname, class_code, editText.text.toString())
                        Log.d("Check Insertion", "Username: " + uname + "    Full Name: " + editText.text.toString())
                        Toast.makeText(applicationContext, "Attendance submitted", Toast.LENGTH_SHORT).show()
                    }
                    setNegativeButton("Cancel") {dialog, which ->
                        Log.d("Main", "Negative button clicked")
                    }
                    setView(dialogLayout)
                    show()
                }
            } else {
                setMessage("Incorrect lecture code")
                show()
            }
        }
    }

}