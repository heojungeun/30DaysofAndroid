package com.example.mybookmark.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mybookmark.data.model.Memo

@Dao
interface MemoDao {

    @Query("SELECT * FROM memo ORDER BY bname ASC")
    fun getAll(): LiveData<List<Memo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memo: Memo)

    @Delete
    fun delete(memo: Memo)

    @Query("DELETE FROM memo")
    fun deleteAll()
}