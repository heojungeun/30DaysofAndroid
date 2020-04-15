package com.example.mybookmark

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MemoDao {

    @Query("SELECT * FROM memo ORDER BY bname ASC")
    fun getAll(): LiveData<List<Memo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memo: Memo)

    @Delete
    fun delete(memo: Memo)
}