package com.example.ourgithubcontributions.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "username")
    var userName: String,

    @ColumnInfo(name = "cblist")
    var cbList: List<ContributionsDay>,

    @ColumnInfo(name = "ismine")
    var isMine: Int
)