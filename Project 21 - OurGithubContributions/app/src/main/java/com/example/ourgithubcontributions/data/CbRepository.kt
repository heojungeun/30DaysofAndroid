package com.example.ourgithubcontributions.data

interface CbRepository {
    fun getAll(): List<User>
    fun deleteAll()
    fun delete(user: User)
    fun insertUser(user: User)
}