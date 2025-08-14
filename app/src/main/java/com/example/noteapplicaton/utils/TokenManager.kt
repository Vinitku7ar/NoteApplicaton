package com.example.noteapplicaton.utils

import android.content.Context
import com.example.noteapplicaton.utils.Constants.PREFS_TOKEN_FILE
import com.example.noteapplicaton.utils.Constants.USER_TOEKN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private var prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOEKN, token)
        editor.apply()

    }
    fun getToken(): String? {
        return prefs.getString(USER_TOEKN, null)
    }

}