package com.nezspencer.fairlistapp.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nezspencer.fairlistapp.data.Job

@Database(entities = [Job::class], version = 1)
abstract class FairListDb : RoomDatabase() {

    abstract fun jobDao(): JobDao

    companion object {
        fun create(app: Application): FairListDb {
            return Room.databaseBuilder(app.applicationContext, FairListDb::class.java, "fair-db")
                .build()
        }
    }
}