package com.sameera.displaysocial.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtils {

    companion object {
        private val sharedPrefUser = "USER_SHARED_PREF_KEY"
        private val userKey = "USER_KEY"

        fun saveUserSharedPrefs(context: Context, userName: String) {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(sharedPrefUser, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(userKey, userName)
            editor.apply()
            editor.commit()
        }

        fun getUserSharedPrefs(context: Context): String? {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(sharedPrefUser, Context.MODE_PRIVATE)
            return sharedPreferences.getString(userKey, null)
        }
    }
}

