package com.example.ourgithubcontributions

import android.content.ContentProviderOperation
import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceManager {

    private const val PREF_TOKEN = "username"
    private const val NAME = "MainUser"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context){
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit){
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var token: String
    get() = preferences.getString(PREF_TOKEN, "").toString()
    set(value) = preferences.edit{
        it.putString(PREF_TOKEN, value)
    }
}