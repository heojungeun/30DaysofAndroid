package com.example.ourgithubcontributions.data

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import android.net.Uri
import com.example.ourgithubcontributions.data.DBHelper.Companion.TABLE_NAME

class ContributionsProvider : ContentProvider() {

    var db: SQLiteDatabase? = null

    override fun onCreate(): Boolean {
        db = context?.let { DBHelper(it).getDB() }
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return db?.query(
            TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
    }

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val row = db?.insertWithOnConflict(TABLE_NAME, null, values, CONFLICT_REPLACE)
        if (row != null) {
            if(row > 0) context?.contentResolver?.notifyChange(uri, null)
        }
        return uri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val count = db?.delete(TABLE_NAME, selection, selectionArgs) ?:0
        if (count > 0) context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val count = db?.update(TABLE_NAME, values, selection, selectionArgs)
        if (count != null) {
            if (count > 0) context?.contentResolver?.notifyChange(uri, null)
            return count
        }
        return 0
    }
}