package com.example.mvvmtupix.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvmtupix.data.local.TupixDao
import com.example.mvvmtupix.model.Movie

@Database(entities = arrayOf(Movie::class), version = 4)
abstract class TupixDatabase : RoomDatabase() {
    abstract fun movieDao(): TupixDao

    companion object {
        @Volatile
        private var INSTANCE: TupixDatabase? = null

        fun getAppDataBase(context: Context): TupixDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TupixDatabase::class.java,
                    "movies_DB"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
