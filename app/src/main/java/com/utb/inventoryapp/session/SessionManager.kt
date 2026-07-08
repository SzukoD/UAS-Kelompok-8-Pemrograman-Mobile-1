package com.utb.inventoryapp.session

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveSession(userId: Long, username: String, namaLengkap: String, role: String) {
        prefs.edit()
            .putLong(KEY_USER_ID, userId)
            .putString(KEY_USERNAME, username)
            .putString(KEY_NAMA_LENGKAP, namaLengkap)
            .putString(KEY_ROLE, role)
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .apply()
    }

    fun getUserId(): Long = prefs.getLong(KEY_USER_ID, -1L)
    fun getUsername(): String = prefs.getString(KEY_USERNAME, "") ?: ""
    fun getNamaLengkap(): String = prefs.getString(KEY_NAMA_LENGKAP, "") ?: ""
    fun getRole(): String = prefs.getString(KEY_ROLE, "") ?: ""
    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val PREF_NAME = "inventory_session"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USERNAME = "username"
        private const val KEY_NAMA_LENGKAP = "nama_lengkap"
        private const val KEY_ROLE = "role"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }
}
