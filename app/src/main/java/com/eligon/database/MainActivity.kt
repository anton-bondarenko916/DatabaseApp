package com.eligon.database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.eligon.database.database.StudentDao
import com.eligon.database.database.StudentDatabase
import com.eligon.database.database.StudentDatabaseHelper
import com.eligon.database.databinding.ActivityMainBinding
import com.eligon.database.model.Student
import com.eligon.database.model.StudentEntity
import com.eligon.database.model.StudentService
import com.eligon.database.model.StudentsListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StudentsAdapter

    private lateinit var studentsService: StudentService
    private lateinit var studentDatabaseHelper: StudentDatabaseHelper



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        studentDatabaseHelper = StudentDatabaseHelper(this, "students", 1)

        Handler().postDelayed({

        },3000)



        studentsService = StudentService(studentDatabaseHelper)
        studentsService.students = studentDatabaseHelper.getStudents()


        adapter = StudentsAdapter(object : StudentActionListener {

            override fun onStudentDelete(student: Student) {
                studentsService.deleteStudent(student)
            }
            override fun onStudentDetails(student: Student) {
                Toast.makeText(this@MainActivity, "${adapter.students.size}", Toast.LENGTH_SHORT).show()
            }
        })

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewStudents.layoutManager = layoutManager
        binding.recyclerViewStudents.adapter = adapter

        studentsService.addListener(usersListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        studentsService.removeListener(usersListener)
        studentDatabaseHelper.close()
    }

    private val usersListener: StudentsListener = {
        adapter.students = it
    }
}