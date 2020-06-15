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
    fun deleteAll()

    @Delete
    fun delete(user: User?)

    @Query("DELETE FROM User WHERE username in (:list)")
    fun deleteItems(list: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

}