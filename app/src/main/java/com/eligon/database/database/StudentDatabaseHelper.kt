package com.eligon.database.database


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.eligon.database.model.Student
import com.eligon.database.model.StudentEntity
import com.eligon.database.model.StudentService


class StudentDatabaseHelper(context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE STUDENTS (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "weight INTEGER, " +
                    "height INTEGER, " +
                    "age INTEGER," +
                    "photo TEXT);"
        )

        for (i in 1..100){
            insertStudent(database, StudentService.generateRandomStudent())
        }
    }

    @SuppressLint("Range", "Recycle")
    fun getStudents(): MutableList<Student> {

        val students = mutableListOf<Student>()

        val database = this.readableDatabase
        val cursor = database.query(
            "STUDENTS",
            arrayOf("id", "name", "weight", "height", "age", "photo"),
            null,
            null,
            null,
            null,
            "age"
            )

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val weight = cursor.getInt(cursor.getColumnIndex("weight"))
            val height = cursor.getInt(cursor.getColumnIndex("height"))
            val age = cursor.getInt(cursor.getColumnIndex("age"))
            val photo = cursor.getString(cursor.getColumnIndex("photo"))

            students.add(Student(id, name, weight, height, age, photo))
        }

        cursor.close()
        return students
    }

    private fun insertStudent(database: SQLiteDatabase, student: Student) {
        val studentValues = ContentValues()
        studentValues.put("id", student.id)
        studentValues.put("name", student.name)
        studentValues.put("weight", student.weight)
        studentValues.put("height", student.height)
        studentValues.put("age", student.age)
        studentValues.put("photo", student.photo)
        database.insert("STUDENTS", null, studentValues)
    }

    fun deleteStudent(database: SQLiteDatabase, student: Student) {
        database.delete("STUDENTS", "id = ?", arrayOf(student.id.toString()))
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}