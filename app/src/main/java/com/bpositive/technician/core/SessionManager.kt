package com.bpositive.technician.core

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    // Session File Name
    val FILE_NAME = "com.bpositive.app.preferences.file"

    val preferences: SharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    fun saveSession(key: String, value: Any?) {
        when (value) {
            is String? -> preferences.edit().putString(key, value).apply()
            is Int -> preferences.edit().putInt(key, value).apply()
            is Boolean -> preferences.edit().putBoolean(key, value).apply()
            is Float -> preferences.edit().putFloat(key, value).apply()
            is Long -> preferences.edit().putLong(key, value).apply()

        }
    }

    fun getSession(key: String): String? = preferences.getString(key, "")

    fun getSessionInt(key: String): Int = preferences.getInt(key, 0)

    fun getSessionBoolean(key: String): Boolean = preferences.getBoolean(key, false)

    fun getSessionFloat(key: String): Float = preferences.getFloat(key, 0F)

    fun getSessionLong(key: String): Long = preferences.getLong(key, 0L)


    /*fun getSession(key: String): String? {

        *//*when (defaultValue) {

            is String? -> {
                return preferences.getString(key, defaultValue)
            }
            is Int -> {
                return preferences.getInt(key, defaultValue)
            }
            is Boolean -> {
                return preferences.getBoolean(key, defaultValue)
            }
            is Float -> {
                return preferences.getFloat(key, defaultValue)
            }
            is Long -> {
                return preferences.getLong(key, defaultValue)
            }

            else -> throw UnsupportedOperationException("Not yet implemented")
        }*//*

        return preferences.getString(key, "")
    }*/




    /*override fun saveLoginStatus(isLoggedIn: Boolean) {
    preferences.edit().putBoolean(IS_LOGGED_IN, isLoggedIn).apply()
    }

    override fun getLoginStatus(): Boolean {
    return preferences.getBoolean(IS_LOGGED_IN, false)
    }*/

    /*inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    operator fun SharedPreferences.set(key: String, value: Any?) {

        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }

    }*/

}

/*interface ISessionManager {

    fun saveLoginStatus(isLoggedIn: Boolean)
    fun getLoginStatus(): Boolean


    companion object {

        // Session File Name
        val FILE_NAME = "com.bpositive.app.preferences.file"

        fun getSessionManagerInstance(context: Context): ISessionManager = SessionManager(context)
    }

}*/
