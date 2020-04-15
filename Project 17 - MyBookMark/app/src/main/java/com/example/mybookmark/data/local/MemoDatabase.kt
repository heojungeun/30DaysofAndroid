package com.example.mybookmark.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mybookmark.data.local.dao.MemoDao
import com.example.mybookmark.data.model.Memo

@Database(entities = [Memo::class], version = 1)
abstract class MemoDatabase: RoomDatabase() {

    abstract fun memoDao(): MemoDao

    companion object {
        private var INSTANCE : MemoDatabase? = null

        fun getInstance(context: Context): MemoDatabase? {
            if (INSTANCE == null){
                synchronized(MemoDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            MemoDatabase::class.java, "memo")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE
        }
    }
}