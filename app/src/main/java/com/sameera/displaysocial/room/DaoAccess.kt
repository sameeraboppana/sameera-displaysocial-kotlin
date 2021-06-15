package com.sameera.displaysocial.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sameera.displaysocial.model.User

@Dao
interface DaoAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(users: List<User>)

    @Query("SELECT * FROM USER")
    suspend fun getUsersList(): List<User>
}