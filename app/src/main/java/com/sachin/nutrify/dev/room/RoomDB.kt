package com.sachin.nutrify.dev.room

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.TypeConverters
import com.sachin.nutrify.dev.model.FUser
import com.sachin.nutrify.dev.model.User

@Database(entities = [
    User::class,
      ]
    ,version = 4, exportSchema = false)

//@TypeConverters(Converters::class)
abstract class RoomDB : RoomDatabase() {

    abstract fun userDetailDao(): UserDetailDao

    companion object {

        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getDatabase(context: Context): RoomDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "chat_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}