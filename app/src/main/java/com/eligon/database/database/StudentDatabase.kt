package com.eligon.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eligon.database.model.StudentEntity

@Database (
    version = 1,
    entities = [StudentEntity::class]
)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun getStudentDao(): StudentDao
}