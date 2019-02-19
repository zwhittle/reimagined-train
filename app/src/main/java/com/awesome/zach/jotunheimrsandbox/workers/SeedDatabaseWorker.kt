package com.awesome.zach.jotunheimrsandbox.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.awesome.zach.jotunheimrsandbox.db.AppDatabase
import com.awesome.zach.jotunheimrsandbox.db.DBSeeder


class SeedDatabaseWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val LOG_TAG by lazy { SeedDatabaseWorker::class.java.simpleName }

    override fun doWork(): Result {
        return try {
            val db = AppDatabase.getDatabase(applicationContext)
//            val dbSeeder = DBSeeder.getInstance(applicationContext)
            val dbSeeder = DBSeeder(db)

            seedColors(db, dbSeeder)
            seedTags(db, dbSeeder)
            seedProjects(db, dbSeeder)
            seedTasks(db, dbSeeder)
            seedTaskTagAssignments(db, dbSeeder)
            
            
            Result.success()
        } catch (ex: Exception) {
            Log.e(LOG_TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    @Throws(Exception::class)
    private fun seedColors(db: AppDatabase, seeder : DBSeeder) : Int {
        val colorsToSeed = seeder.populateColorsList()
        val insertedIds = db.colorDao().bulkInsertColors(colorsToSeed)
        
        val seedCount = colorsToSeed.size
        val insertedCount = insertedIds.size
        
        if (insertedCount == seedCount) {
            Log.d(LOG_TAG, "Successfully seeded $insertedCount colors!")
            return insertedCount
        } else {
            throw Exception("insertedCount does not match seedCount")
        }        
    }
    
    @Throws(Exception::class)
    private fun seedTags(db: AppDatabase, seeder: DBSeeder) : Int {
        val tagsToSeed = seeder.populateTagsList()
        val insertedIds = db.tagDao().bulkInsertTags(tagsToSeed)
        
        val seedCount = tagsToSeed.size
        val insertedCount = insertedIds.size
        
        if (insertedCount == seedCount) {
            Log.d(LOG_TAG, "Successfully seeded $insertedCount tags!")
            return insertedCount
        } else {
            throw Exception("insertedCount does not match seedCount")
        }
    }

    @Throws(Exception::class)
    private fun seedProjects(db: AppDatabase, seeder: DBSeeder) : Int {
        val projectsToSeed = seeder.populateProjectsList()
        val insertedIds = db.projectDao().bulkInsertProjects(projectsToSeed)

        val seedCount = projectsToSeed.size
        val insertedCount = insertedIds.size

        if (insertedCount == seedCount) {
            Log.d(LOG_TAG, "Successfully seeded $insertedCount projects!")
            return insertedCount
        } else {
            throw Exception("insertedCount does not match seedCount")
        }
    }

    @Throws(Exception::class)
    private fun seedTasks(db: AppDatabase, seeder: DBSeeder) : Int {
        val tasksToSeed = seeder.populateTasksList(true)
        val insertedIds = db.taskDao().bulkInsertTasks(tasksToSeed)

        val seedCount = tasksToSeed.size
        val insertedCount = insertedIds.size

        if (insertedCount == seedCount) {
            Log.d(LOG_TAG, "Successfully seeded $insertedCount tasks!")
            return insertedCount
        } else {
            throw Exception("insertedCount does not match seedCount")
        }
    }

    @Throws(Exception::class)
    private fun seedTaskTagAssignments(db: AppDatabase, seeder: DBSeeder) : Int {
        val taskTagAssignmentsToSeed = seeder.populateTaskTagAssignmentsList()
        val insertedIds = db.taskTagAssignmentDao().bulkInsertTaskTagAssignments(taskTagAssignmentsToSeed)

        val seedCount = taskTagAssignmentsToSeed.size
        val insertedCount = insertedIds.size

        if (insertedCount == seedCount) {
            Log.d(LOG_TAG, "Successfully seeded $insertedCount taskTagAssignments!")
            return insertedCount
        } else {
            throw Exception("insertedCount does not match seedCount")
        }
    }
}