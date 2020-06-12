package com.example.ourgithubcontributions.data

import androidx.lifecycle.LiveData
import com.example.ourgithubcontributions.data.model.User
import io.reactivex.Completable

interface CbRepository {
    fun getAll(): LiveData<List<User>>
    fun deleteAll(): Completable
    fun delete(user: User): Completable
    fun insertUser(user: User): Completable
}