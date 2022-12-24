package com.example.attendance_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class InstructorOrStudent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_or_student)

        val instructorbutton = findViewById<Button>(R.id.button5)
        instructorbutton.setOnClickListener {
            val Intent1 = Intent(this, Instructor_SignUp::class.java)
            startActivity(Intent1)
        }

        val studentbutton = findViewById<Button>(R.id.button6)
        studentbutton.setOnClickListener {
            val Intent2 = Intent(this, Student_SignUp::class.java)
            startActivity(Intent2)
        }
    }
}