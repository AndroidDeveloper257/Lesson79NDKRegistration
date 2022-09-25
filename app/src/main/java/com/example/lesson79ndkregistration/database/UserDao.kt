package com.example.lesson79ndkregistration.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun addUser(user: User)

    @Query("SELECT * FROM user_table WHERE id = :id")
    fun getUserById(id: Long): User

    @Query("SELECT * FROM user_table")
    fun listUsers(): List<User>
}