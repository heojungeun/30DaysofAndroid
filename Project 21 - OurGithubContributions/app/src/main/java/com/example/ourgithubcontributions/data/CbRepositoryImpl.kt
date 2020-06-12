package com.example.ourgithubcontributions.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.ourgithubcontributions.data.model.User
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

    override fun delete(user: User): Completable {
        return cbDao.delete(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteAll(): Completable {
        return cbDao.deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun insertUser(user: User): Completable {
        return cbDao.insertUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}