package com.eligon.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val age: Int,
    val photo: String) {

    fun toStudent() : Student {
        return Student(id, name, weight, height, age, photo)
    }
}