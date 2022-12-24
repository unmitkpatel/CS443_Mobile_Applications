package com.example.attendance_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Loginbutton = findViewById<Button>(R.id.button3)
        Loginbutton.setOnClickListener {
            val Intent1 = Intent(this,LogIn::class.java)
            startActivity(Intent1)
        }

        val Signupbutton = findViewById<Button>(R.id.button4)
        Signupbutton.setOnClickListener {
            var check = Instructors_Credentials(this).fetchCredentialData()
            for (i in check) {
                Log.d("Users", "Name: " + i.Username)
            }
            val Intent2 = Intent(this, InstructorOrStudent::class.java)
            startActivity(Intent2)
        }
    }
}


