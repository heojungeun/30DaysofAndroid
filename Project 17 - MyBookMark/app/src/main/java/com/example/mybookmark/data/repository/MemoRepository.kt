package com.example.mybookmark.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mybookmark.data.local.MemoDatabase
import com.example.mybookmark.data.local.dao.MemoDao
import com.example.mybookmark.data.model.Memo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MemoRepository(application: Application) : CoroutineScope{

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val memoDatabase = MemoDatabase.getInstance(application)!!
    private val memoDao: MemoDao = memoDatabase.memoDao()
    private val memos: LiveData<List<Memo>> = memoDao.getAll()

    fun getAll(): LiveData<List<Memo>>{
        return memos
    }

    fun insert(memo: Memo){
        launch { insertBG(memo) }
    }

    private suspend fun insertBG(memo: Memo){
        withContext(Dispatchers.IO){
            memoDao?.insert(memo)
        }
    }

    fun delete(memo: Memo){
        launch { deleteBG(memo) }
    }

    private suspend fun deleteBG(memo: Memo){
        withContext(Dispatchers.IO){
            memoDao?.delete(memo)
        }
    }

}