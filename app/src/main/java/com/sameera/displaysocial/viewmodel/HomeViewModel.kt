package com.sameera.displaysocial.viewmodel

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.sameera.displaysocial.activity.R
import com.sameera.displaysocial.utils.SharedPreferenceUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class HomeViewModel : ViewModel() {

    public fun isUserNameSaved(context: Context): Boolean {
        return !TextUtils.isEmpty(SharedPreferenceUtils.getUserSharedPrefs(context))
    }

    private fun getUserName(context: Context): String? {
        return SharedPreferenceUtils.getUserSharedPrefs(context)
    }

    fun saveUserName(context: Context, username: String) {
        if (!isUserNameSaved(context)) {
            SharedPreferenceUtils.saveUserSharedPrefs(context, username)
        }
    }

    fun getGreetingMessage(context: Context): String {
        return getGreetTimeMessage(context) + " " + getUserName(context)
    }

    private fun getGreetTimeMessage(context: Context): String {
        val dateFormat: DateFormat = SimpleDateFormat("k")
        var currentTime = dateFormat.format(Date()).toInt();
        if (currentTime in 6..12) {
            return context.getString(R.string.good_morning)
        } else if (currentTime in 12..17) {
            return context.getString(R.string.good_afternoon)
        } else if (currentTime in 17..24) {
            return context.getString(R.string.good_evening)
        } else {
            return context.getString(R.string.hello)
        }
    }
}