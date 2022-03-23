package com.eligon.database.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.eligon.database.model.Student
import com.eligon.database.model.StudentEntity

@Dao
interface StudentDao {

    @Insert(entity = StudentEntity::class)
    fun insertStudent(student : Student)

    @Query("DELETE FROM students WHERE id = :id")
    fun deleteStudentById(id : Int)

    @Query("SELECT * FROM students")
    fun getStudents() : MutableList<StudentEntity>
}