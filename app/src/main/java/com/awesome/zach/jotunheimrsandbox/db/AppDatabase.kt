package com.awesome.zach.jotunheimrsandbox.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.awesome.zach.jotunheimrsandbox.db.converters.DateTypeConverter
import com.awesome.zach.jotunheimrsandbox.db.daos.*
import com.awesome.zach.jotunheimrsandbox.db.entities.*
import com.awesome.zach.jotunheimrsandbox.utils.Constants.Companion.DB_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Color::class, Tag::class, Project::class, Task::class, TaskTagJoin::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    // abstract Dao functions
    abstract fun colorDao(): ColorDao
    abstract fun tagDao(): TagDao
    abstract fun projectDao(): ProjectDao
    abstract fun taskDao(): TaskDao
    abstract fun taskTagJoinDao(): TaskTagJoinDao

    // companion object that returns a singleton of the DB
    companion object {
        var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME).build()
                }
            }

            return INSTANCE
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }

    private class AppDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let {database ->
                scope.launch(Dispatchers.IO){
                    seedDatabase(database)
                }
            }
        }

        fun seedDatabase(db: AppDatabase) {
            DBSeeder().seedColors(db.colorDao())
        }
    }
}