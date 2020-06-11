package com.example.ourgithubcontributions.data

import androidx.lifecycle.LiveData

interface CbRepository {
    fun getAll(): LiveData<List<User>>
    fun deleteAll()
    fun delete(user: User)
    fun insertUser(user: User)
}