package com.example.attendance_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog

class Instuctor_After_LoginPage : AppCompatActivity() {
    lateinit var linearLayout: LinearLayout
    lateinit var editText: EditText
    lateinit var class_name: String
    private var class_code: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instuctor_after_login_page)

        this.linearLayout = findViewById(R.id.linearLayout)
        val add_class = findViewById<Button>(R.id.button7)


        val fetch_data: SQL_Content_for_Instructor = SQL_Content_for_Instructor(this)
        val class_info: ArrayList<InstructorModel> = fetch_data.fetchInstructorData()
        val getUsername: Instructors_Credentials = Instructors_Credentials(this)
        for(i in class_info) {
            if (i.user_name == getUsername.getUsername().get(getUsername.getUsername().size - 1)) {
                val button = Button(this)
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                button.layoutParams = params
                button.text = i.class_name + "\n Class code: " + i.class_code
                this.linearLayout.addView(button)
                button.setOnClickListener{
                    val build = AlertDialog.Builder(this)
                    val inflater = LayoutInflater.from(this)
                    //val dialogLayout = inflater.inflate(R.layout.layout_for_dialog,null)
                    //val textView = dialogLayout.findViewById<EditText>(R.id.textView9)
                    with(build) {
                        setTitle("Create Code or Check Attendance")
                        setPositiveButton("Attendance") {dialog, which ->
                            secondMessageForAttendance(i.class_code)
                        }
                        setNeutralButton("Code") {dialog, which ->
                            secondMessageForCode(i.class_code)
                        }
                        setNegativeButton("Cancel") {dialog, which ->
                            Log.d("Main", "Negative button clicked")
                        }
                        //setView(dialogLayout)
                        show()
                    }
                }
            }
        }
        add_class.setOnClickListener{
            val build = AlertDialog.Builder(this)
            val inflater = LayoutInflater.from(this)
            val dialogLayout = inflater.inflate(R.layout.layout_for_dialog,null)
            this.editText = dialogLayout.findViewById<EditText>(R.id.textView8)
            with(build) {
                setTitle("Add Class!")
                setPositiveButton("Insert") {dialog, which ->
                    class_name = editText.text.toString()
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
    private fun secondMessageForAttendance(class_code: Int) {
        var fullnames = Username_and_Fullname(this)
        var attendees = fullnames.fetchCredentialData()
        var names_list = ArrayList<String>()
        for (i in attendees) {
            if (i.Classcode == class_code) {
                names_list.add(i.FullName)
            }
        }
        var names = arrayOfNulls<String>(names_list.size)
        for (i in names_list) {
            var index = 0
            names.set(index, names_list.get(index))
            index += 1
        }
        names_list.toArray(names)
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Attendees")
        builder.setItems(names) {dialog, which->
            Toast.makeText(applicationContext, names[which], Toast.LENGTH_SHORT).show()
        }
        var dialog = builder.create()
        dialog.show()
    }

    private fun secondMessageForCode(code: Int) {
        var lecture_code = lectureCode()
        val build = AlertDialog.Builder(this)
        with (build) {
            setMessage("Today's lecture code is:\n" + lecture_code)
            show()
        }
        var classinfo: ClassCode_and_LectureCode = ClassCode_and_LectureCode(this)
        Log.d("Class Code", "Code: " + code)
        classinfo.updateClassData(code, lecture_code)
    }

    private fun lectureCode(): Int {
        val lecture_code = (100000..999999).random()
        return lecture_code
    }

    private fun setClassCode(number: Int) {
        this.class_code = number
    }

    private fun getClassCode(): Int {
        return this.class_code
    }
    private fun createAndAddView(message: String) {
        var random_code = (100000..999999).random()
        class_code = random_code
        setClassCode(random_code)
        val button = Button(this)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        button.layoutParams = params
        button.text = message + "\n Class code: " + class_code
        linearLayout.addView(button)
        var check: Instructors_Credentials = Instructors_Credentials(this)
        val name =  check.getUsername()
        var insert: SQL_Content_for_Instructor = SQL_Content_for_Instructor(this)
        insert.insertInstructor(name[name.size - 1], class_name, class_code)
        var classinfo: ClassName_and_ClassCode = ClassName_and_ClassCode(this)
        classinfo.insertClassinfo(class_name, class_code)
        var lectureinfo: ClassCode_and_LectureCode = ClassCode_and_LectureCode(this)
        lectureinfo.insertClassinfo(class_code, 0)
        Log.d("Test", "Name: " + name + " Class: " + class_name + " Code: " + class_code)
        button.setOnClickListener{
            val build = AlertDialog.Builder(this)
            val inflater = LayoutInflater.from(this)
            //val dialogLayout = inflater.inflate(R.layout.layout_for_dialog,null)
            //val textView = dialogLayout.findViewById<EditText>(R.id.textView9)
            with(build) {
                setTitle("Create Code or Check Attendance")
                setPositiveButton("Attendance") {dialog, which ->
                    secondMessageForAttendance(class_code)
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
}