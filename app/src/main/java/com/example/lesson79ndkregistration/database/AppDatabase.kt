package com.example.lesson79ndkregistration.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context, AppDatabase::class.java, "my_db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }

    /**
    @Database(entities = [EntityClasses::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {

    abstract fun modelDao(): ModelDao

    companion object {
    private var INSTANCE: AppDatabase? = null

    @Synchronized
    fun getInstance(context: Context): AppDatabase {
    if (INSTANCE == null) {
    INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "my_db")
    .allowMainThreadQueries()
    .build()
    }
    return INSTANCE!!
    }
    }
    }
     */
}