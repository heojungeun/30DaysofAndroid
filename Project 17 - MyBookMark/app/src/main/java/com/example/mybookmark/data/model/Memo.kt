package com.example.mybookmark.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName="memo")
data class Memo(
    @PrimaryKey(autoGenerate=true)
    var id : Long?,

    @ColumnInfo(name = "btime")
    var time: String,

    @ColumnInfo(name = "bname")
    var bookname: String,

    @ColumnInfo(name = "bctnt")
    var content: String ="",

    @ColumnInfo(name = "bphotos")
    var photos: String=""
)