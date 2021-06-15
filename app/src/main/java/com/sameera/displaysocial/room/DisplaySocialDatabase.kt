package com.sameera.displaysocial.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sameera.displaysocial.model.User

@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract class DisplaySocialDatabase : RoomDatabase() {

    abstract fun getDao() : DaoAccess

    companion object {

        @Volatile
        private var INSTANCE: DisplaySocialDatabase? = null

        fun getDatabaseClient(context: Context) : DisplaySocialDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, DisplaySocialDatabase::class.java, "DISPLAY_SOCIAL_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }

}