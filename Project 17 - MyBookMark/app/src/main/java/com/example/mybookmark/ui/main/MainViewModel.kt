package com.example.mybookmark.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mybookmark.data.model.Memo
import com.example.mybookmark.data.repository.MemoRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: MemoRepository = MemoRepository(application)

    fun getAll() = repository.getAll()

    fun insert(memo: Memo) {
        repository.insert(memo)
    }

    fun delete(bid : Long) {
        repository.delete(bid)
    }
}