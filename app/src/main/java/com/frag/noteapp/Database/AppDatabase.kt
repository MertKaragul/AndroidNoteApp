package com.frag.noteapp.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Usernotemodel::class] , version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao() : DatabaseDAO
}