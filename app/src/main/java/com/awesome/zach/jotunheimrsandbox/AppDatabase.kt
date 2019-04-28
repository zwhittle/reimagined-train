package com.awesome.zach.jotunheimrsandbox

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.awesome.zach.jotunheimrsandbox.workers.SeedDatabaseWorker

interface AppDatabase {
    fun getDatabase(): BaseDatabase
    fun isDatabaseCreated(): LiveData<Boolean>
}

class AppDatabaseImpl constructor(context: Context): AppDatabase {

    private val db: BaseDatabase

    private val isDatabaseCreated = MutableLiveData<Boolean>()

    init {
        isDatabaseCreated.postValue(false)
        this.db = Room.databaseBuilder(context, BaseDatabase::class.java, "main.db")
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    seedDatabase()
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    isDatabaseCreated.postValue(true)
                }
            })
            .build()
    }

    override fun getDatabase(): BaseDatabase {
        return db
    }

    override fun isDatabaseCreated(): LiveData<Boolean> {
        return isDatabaseCreated
    }

    private fun seedDatabase() {
        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
        WorkManager.getInstance().enqueue(request)
    }

}