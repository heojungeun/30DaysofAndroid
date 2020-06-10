package com.example.ourgithubcontributions.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class CbDatabase: RoomDatabase() {
    abstract fun cbDao(): CbDao

    companion object{
        private const val DB_NAME = "User.db"
        private var INSTANCE: CbDatabase? = null

        fun getInstance(context: Context): CbDatabase{
            if(INSTANCE == null){
                synchronized(CbDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CbDatabase::class.java,
                        DB_NAME
                    ).build()
                }
            }
            return INSTANCE as CbDatabase
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}