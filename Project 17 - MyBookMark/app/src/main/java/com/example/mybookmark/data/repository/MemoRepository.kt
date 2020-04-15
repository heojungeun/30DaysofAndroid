package com.example.mybookmark.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mybookmark.data.local.MemoDatabase
import com.example.mybookmark.data.local.dao.MemoDao
import com.example.mybookmark.data.model.Memo
import java.lang.Exception

class MemoRepository(application: Application) {

    private val memoDatabase = MemoDatabase.getInstance(application)!!
    private val memoDao: MemoDao = memoDatabase.memoDao()
    private val memos: LiveData<List<Memo>> = memoDao.getAll()

    fun getAll(): LiveData<List<Memo>>{
        return memos
    }

    fun insert(memo: Memo){
        try{

            memoDao.insert(memo)
        }catch (e: Exception){}
    }
}