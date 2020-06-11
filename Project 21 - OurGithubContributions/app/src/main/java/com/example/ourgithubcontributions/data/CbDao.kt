package com.example.ourgithubcontributions.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CbDao {
    @Query("SELECT * FROM User")
    fun getAll(): LiveData<List<User>>

    @Query("DELETE FROM User WHERE ismine=0")
    fun deleteAll()

    @Delete
    fun delete(user: User?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

}