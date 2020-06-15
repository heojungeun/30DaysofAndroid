package com.example.ourgithubcontributions.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.ourgithubcontributions.data.model.User
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class CbRepositoryImpl() : CbRepository, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val cbDao = CbDatabase.getInstance().cbDao()

    companion object {
        @Volatile
        private var INSTANCE: CbRepositoryImpl? = null

        fun getInstance() =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: CbRepositoryImpl().also { INSTANCE = it }
            }
    }

    override fun getAll(): LiveData<List<User>> {
        return cbDao.getAll()
    }

    override fun delete(user: User) {
        launch { deleteBG(user) }
    }

    override fun deleteItems(list: List<String>) {
        launch { deleteItemsBG(list) }
    }

    override fun deleteAll() {
        launch { deleteAllBG() }
    }

    override fun insertUser(user: User) {
        launch { setInsertUserBG(user) }
    }

    private suspend fun deleteBG(user: User) {
        withContext(Dispatchers.IO) {
            cbDao.delete(user)
        }
    }

    private suspend fun deleteItemsBG(list: List<String>){
        withContext(Dispatchers.IO){
            cbDao.deleteItems(list)
        }
    }

    private suspend fun deleteAllBG() {
        withContext(Dispatchers.IO) {
            cbDao.deleteAll()
        }
    }

    private suspend fun setInsertUserBG(user: User) {
        withContext(Dispatchers.IO) {
            cbDao.insertUser(user)
        }
    }
}