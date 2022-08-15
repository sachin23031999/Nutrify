package com.sachin.nutrify.dev.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sachin.nutrify.dev.model.User

@Dao
interface UserDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user")
    suspend fun getAllUsers() : LiveData<List<User>>
}