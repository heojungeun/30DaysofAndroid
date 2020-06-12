package com.example.ourgithubcontributions.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ourgithubcontributions.data.model.User
import io.reactivex.Completable

@Dao
interface CbDao {
    @Query("SELECT * FROM User")
    fun getAll(): LiveData<List<User>>

    @Query("DELETE FROM User")
    fun deleteAll(): Completable

    @Delete
    fun delete(user: User?): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Completable

}