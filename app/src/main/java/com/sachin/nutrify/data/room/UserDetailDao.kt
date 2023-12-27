package com.sachin.nutrify.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sachin.nutrify.model.FUser

@Dao
interface UserDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: FUser)

    @Update
    suspend fun updateUser(user: FUser)

    @Delete
    suspend fun deleteUser(user: FUser)

    @Query("SELECT * FROM user")
    suspend fun getAllUsers() : LiveData<List<FUser>>
}