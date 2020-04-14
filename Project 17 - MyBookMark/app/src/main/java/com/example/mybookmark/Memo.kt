package com.example.mybookmark

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="memo")
data class Memo(
    @PrimaryKey(autoGenerate=true)
    var id : Long?,

    @ColumnInfo(name = "bname")
    var bookname: String,

    @ColumnInfo(name = "bctnt")
    var content: String ="",

    @ColumnInfo(name = "bphotos")
    var photos: String=""
)