package com.example.ourgithubcontributions.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ourgithubcontributions.data.CbRepositoryInjector
import com.example.ourgithubcontributions.data.model.User

class UserViewModel() {

    private val cbinstance = CbRepositoryInjector.getCbRepositoryImpl()

    val userList: LiveData<List<User>> = cbinstance.getAll()

    fun insertUser(user: User){
        cbinstance.insertUser(user)
    }

    fun deleteAll(){
        cbinstance.deleteAll()
    }
}