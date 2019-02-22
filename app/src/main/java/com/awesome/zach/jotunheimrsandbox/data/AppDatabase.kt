package com.awesome.zach.jotunheimrsandbox.data

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.awesome.zach.jotunheimrsandbox.data.converters.DateTypeConverter
import com.awesome.zach.jotunheimrsandbox.data.daos.*
import com.awesome.zach.jotunheimrsandbox.data.entities.*
import com.awesome.zach.jotunheimrsandbox.utils.Constants
import com.awesome.zach.jotunheimrsandbox.workers.SeedDatabaseWorker

@Database(entities = [Color::class, Tag::class, Project::class, Task::class, TaskTagAssignment::class],
          version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    // abstract Dao functions
    abstract fun colorDao(): ColorDao

    abstract fun tagDao(): TagDao
    abstract fun projectDao(): ProjectDao
    abstract fun taskDao(): TaskDao
    abstract fun taskTagAssignmentDao(): TaskTagAssignmentDao

    // companion object that returns a singleton of the DB
    companion object {
        var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context,
                                        AppDatabase::class.java,
                                        Constants.DB_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        WorkManager.getInstance()
                            .enqueue(request)
                    }
                })
                .build()
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}