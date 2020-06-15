package com.example.ourgithubcontributions.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "username")
    var userName: String,

    @ColumnInfo(name = "isSelected")
    var isSelected: Boolean
)