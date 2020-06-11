package com.example.ourgithubcontributions.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import kotlin.coroutines.CoroutineContext

class CbRepositoryImpl(application: Application) : CbRepository, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val cbDao = CbDatabase.getInstance(application).cbDao()

    companion object{
        @Volatile
        private var INSTANCE: CbRepositoryImpl? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: CbRepositoryImpl(application).also { INSTANCE = it }
            }
    }

    override fun getAll(): LiveData<List<User>> {
        return cbDao.getAll()
    }

    override fun delete(user: User) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun insertUser(user: User) {
        TODO("Not yet implemented")
    }
}