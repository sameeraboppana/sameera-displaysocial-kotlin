package com.sameera.displaysocial.repository

import android.content.Context
import com.google.gson.Gson
import com.sameera.displaysocial.model.User
import com.sameera.displaysocial.model.UserData
import com.sameera.displaysocial.model.UserResponse
import com.sameera.displaysocial.room.DisplaySocialDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepository {

    companion object {

        /**
         * get the users from room db, if the users are not available in room db, fet it from service.
         */
        suspend fun getUsers(context: Context): List<User> {
            val usersLiveData = getUsersFromDatabase(context)
            if(usersLiveData.isNullOrEmpty()) {
                val users = makeGetUsersHttpGetCall().users
                insertUsersInDb(context, users)
                return users
            }
            return usersLiveData
        }

        fun insertUsersInDb(context: Context, users: List<User>) {
            val loginDatabase = DisplaySocialDatabase.getDatabaseClient(context)

            CoroutineScope(IO).launch {
                loginDatabase.getDao().insertAllUsers(users)
            }
        }

        private suspend fun getUsersFromDatabase(context: Context) : List<User> {
            val loginDatabase = DisplaySocialDatabase.getDatabaseClient(context)
            return loginDatabase.getDao().getUsersList()
        }

        private suspend fun makeGetUsersHttpGetCall(): UserData {

            return withContext(Dispatchers.IO) {
                val inputStream: InputStream
                val url = URL("https://run.mocky.io/v3/b852db38-d936-4880-b4ab-13361debe270")
                val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                conn.connect()
                // receive response as inputStream
                inputStream = conn.inputStream

                if (inputStream != null) {
                    val jsonString = convertInputStreamToString(inputStream)
                    Gson().fromJson(jsonString, UserResponse::class.java).data
                }
                else
                    UserData(emptyList())
            }
        }

        private fun convertInputStreamToString(inputStream: InputStream): String {
            val bufferedReader: BufferedReader? = BufferedReader(InputStreamReader(inputStream))

            var line:String? = bufferedReader?.readLine()
            var result = ""

            while (line != null) {
                result += line
                line = bufferedReader?.readLine()
            }

            inputStream.close()
            return result
        }
    }
}