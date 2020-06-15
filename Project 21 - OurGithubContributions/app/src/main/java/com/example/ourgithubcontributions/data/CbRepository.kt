package com.example.ourgithubcontributions.data

import androidx.lifecycle.LiveData
import com.example.ourgithubcontributions.data.model.User
import io.reactivex.Completable

interface CbRepository {
    fun getAll(): LiveData<List<User>>
    fun deleteAll()
    fun delete(user: User)
    fun deleteItems(list: List<String>)
    fun insertUser(user: User)
}