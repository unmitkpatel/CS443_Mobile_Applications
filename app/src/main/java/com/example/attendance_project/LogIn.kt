package com.example.attendance_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

class LogIn : AppCompatActivity() {
    lateinit var username_input: EditText
    lateinit var password_input: EditText
    lateinit var createaccountbutton: Button
    lateinit var checkBox: CheckBox
    var signup: Instructor_SignUp = Instructor_SignUp()
    lateinit var db: Instructors_Credentials
    //private var check: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val context = this
        this.username_input = findViewById<EditText>(R.id.editTextTextPersonName)
        this.password_input = findViewById<EditText>(R.id.editTextTextPassword)
        this.createaccountbutton = findViewById<Button>(R.id.button)

        this.username_input.addTextChangedListener(loginTextWatcher)
        this.password_input.addTextChangedListener(loginTextWatcher)
        db = Instructors_Credentials(this)
        var instructors = Instructors_Credentials(this).fetchCredentialData()
        var students = Students_Credentials(this).fetchCredentialData()
        var all_credentials = ArrayList<Credentials>()
        all_credentials.addAll(instructors)
        all_credentials.addAll(students)
        var all_usernames = ArrayList<String>()
        for (i in instructors) {
            all_usernames.add(i.Username)
        }
        for (i in students) {
            all_usernames.add(i.Username)
        }
        for (i in all_usernames) {
            Log.d("Users", "Name" + i)
        }
        createaccountbutton.setOnClickListener {
            var check: Boolean = false
            var cred_instructor = Instructors_Credentials(this)
            var cred_student: Students_Credentials = Students_Credentials(this)
            if (!all_usernames.contains(username_input.text.toString())) {
                Toast.makeText(context, "No such user found", Toast.LENGTH_SHORT).show()
            } else {
                for (i in cred_instructor.fetchCredentialData()) {
                    Log.d("Instructors", "Name: " + i.Username)
                    if (i.Username == username_input.text.toString()) {
                        check = true
                    }
                }
                if (check) {
                    val method = Instructors_Credentials(this)
                    method.setUsername(username_input.text.toString())
                    val Intent1 = Intent(this, Instuctor_After_LoginPage::class.java)
                    startActivity(Intent1)
                } else {
                    val method = Students_Credentials(this)
                    method.setUsername(username_input.text.toString())
                    var full_name = Username_and_Fullname(this)
                    full_name.setUsername(username_input.text.toString())
                    val Intent1 = Intent(this, Student_After_LoginPage::class.java)
                    startActivity(Intent1)
                }
            }
        }
    }

    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val username = username_input.text.toString().trim()
            val password = password_input.text.toString().trim()
            createaccountbutton.isEnabled = username.isNotEmpty() && password.isNotEmpty()
        }

        override fun afterTextChanged(p0: Editable?) {}
    }
}