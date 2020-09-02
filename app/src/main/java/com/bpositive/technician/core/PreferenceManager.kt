package com.bpositive.technician.core

import android.content.Context
import android.content.SharedPreferences
import com.bpositive.technician.utils.sessionNames.TECHNICIAN_ID
import com.bpositive.technician.utils.sessionNames.TECHNICIAN_NAME
import com.bpositive.technician.utils.sessionNames.USER_LANGUAGE

class PreferenceManager(context: Context) {

    private val FILE_NAME = "com.bpositive.technician"

    private val preferences: SharedPreferences =
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

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

    fun saveLanguage(language: String) =
        preferences.edit().putString(USER_LANGUAGE, language).apply()

    fun getLanguage() = preferences.getString(USER_LANGUAGE, "")

    fun saveTechnicianId(technicianId: Int) =
        preferences.edit().putInt(TECHNICIAN_ID, technicianId).apply()

    fun getTechnicianId() = preferences.getInt(TECHNICIAN_ID, 0)

    fun saveTechnicianName(technicianName: String) =
        preferences.edit().putString(TECHNICIAN_NAME, technicianName).apply()

    fun getTechnicianName() = preferences.getString(TECHNICIAN_NAME, "")

}