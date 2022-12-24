package com.example.attendance_project

import  android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Student_SignUp : AppCompatActivity() {
    lateinit var usernameInput: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_sign_up)

        var createAccountButton = findViewById<Button>(R.id.button2)
        usernameInput = findViewById<EditText>(R.id.EditTextStudentUserName)
        var passwordInput = findViewById<EditText>(R.id.editTextTextPassword2)
        var credentialsdb: Students_Credentials
        credentialsdb = Students_Credentials(this)
        createAccountButton.setOnClickListener {
            if (usernameInput.text.toString().isEmpty() || passwordInput.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter all credentials", Toast.LENGTH_SHORT).show()
            } else if(usernameInput.text.toString().length < 5) {
                Toast.makeText(this, "Username should be 5 characters long", Toast.LENGTH_SHORT).show()
            }else if(passwordInput.text.toString().length < 7) {
                Toast.makeText(this, "Password should be 7 characters long", Toast.LENGTH_SHORT).show()
            } else {
                var instructor_cred = Instructors_Credentials(this).fetchCredentialData()
                var student_cred = Students_Credentials(this).fetchCredentialData()
                var login_cred = ArrayList<Credentials>()
                login_cred.addAll(instructor_cred)
                login_cred.addAll(student_cred)
                var check = false
                for(i in login_cred) {
                    if (i.Username == usernameInput.text.toString()) {
                        check = true
                    }
                }
                if (check) {
                    Toast.makeText(this,"Username already taken", Toast.LENGTH_SHORT).show()
                } else {
                    credentialsdb.insertCredentials(usernameInput.text.toString(), passwordInput.toString())
                    var full_name = Username_and_Fullname(this)
                    full_name.setUsername(usernameInput.text.toString())
                    val method: SQL_Content_for_Student = SQL_Content_for_Student(this)
                    method.setUsername(usernameInput.text.toString())
                    val Intent = Intent(this, Student_MainPage::class.java)
                    startActivity(Intent)
                }
            }
        }
    }
}