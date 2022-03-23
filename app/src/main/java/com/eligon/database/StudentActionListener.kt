package com.eligon.database

import com.eligon.database.model.Student
import com.eligon.database.model.StudentEntity

interface StudentActionListener {
    fun onStudentDelete(student: Student)
    fun onStudentDetails(student: Student)
}