package com.awesome.zach.jotunheimrsandbox.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.awesome.zach.jotunheimrsandbox.AppDatabase
import com.awesome.zach.jotunheimrsandbox.BaseDatabase
import com.awesome.zach.jotunheimrsandbox.data.DBSeeder
import com.awesome.zach.jotunheimrsandbox.data.entities.JHList
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class SeedDatabaseWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams), KoinComponent {

    private val LOG_TAG by lazy { SeedDatabaseWorker::class.java.simpleName }

    override fun doWork(): Result {

        val db by inject<AppDatabase>()

        return try {

            val baseDb = db.getDatabase()
            val dbSeeder = DBSeeder(baseDb)

            // seedColors(baseDb, dbSeeder)
            seedLists(baseDb, dbSeeder)
            // seedTags(db, dbSeeder)
            // seedProjects(db, dbSeeder)
            // seedTasks(db, dbSeeder)
            // seedTaskTagAssignments(db, dbSeeder)
            
            
            Result.success()
        } catch (ex: Exception) {
            Log.e(LOG_TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    // @Throws(Exception::class)
    // private fun seedColors(db: BaseDatabase, seeder : DBSeeder) : Int {
    //     // val colorsToSeed = seeder.populateColorsList()
    //     // val colors = ArrayList(db.getDatabase().colorDao().listNow())
    //     //
    //     // val insertedIds = db.getDatabase().colorDao().
    //     // seeder.colors = colors
    //     //
    //     // val seedCount = colorsToSeed.size
    //     // val insertedCount = insertedIds.size
    //     //
    //     // if (insertedCount == seedCount) {
    //     //     Log.d(LOG_TAG, "Successfully seeded $insertedCount colors!")
    //     //     return insertedCount
    //     // } else {
    //     //     throw Exception("insertedCount does not match seedCount")
    //     // }
    // }

    @Throws(Exception::class)
    private fun seedLists(db: BaseDatabase, seeder: DBSeeder) : Int {
        val listsToSeed = seeder.populateListsList()
        val listsArray = arrayOf<JHList>()
        val lists = listsToSeed.toArray(listsArray)

        val insertedIds = db.listDao().insertAll(*lists)
        seeder.lists = ArrayList(db.listDao().listNow())

        val seedCount = listsToSeed.size
        val insertedCount = insertedIds.size

        if (insertedCount == seedCount) {
            Log.d(LOG_TAG, "Successfully seeded $insertedCount lists!")
            return insertedCount
        } else {
            throw Exception("insertedCount does not match seedCount")
        }
    }
    
    // @Throws(Exception::class)
    // private fun seedTags(db: AppDatabase, seeder: DBSeeder) : Int {
    //     // val tagsToSeed = seeder.populateTagsList()
    //     // val insertedIds = db.tagDao().bulkInsertTags(tagsToSeed)
    //     // seeder.tags = ArrayList(db.tagDao().getAllTags())
    //     //
    //     // val seedCount = tagsToSeed.size
    //     // val insertedCount = insertedIds.size
    //     //
    //     // if (insertedCount == seedCount) {
    //     //     Log.d(LOG_TAG, "Successfully seeded $insertedCount tags!")
    //     //     return insertedCount
    //     // } else {
    //     //     throw Exception("insertedCount does not match seedCount")
    //     // }
    // }

    // @Throws(Exception::class)
    // private fun seedProjects(db: AppDatabase, seeder: DBSeeder) : Int {
    //     val projectsToSeed = seeder.populateProjectsList()
    //     val insertedIds = db.projectDao().bulkInsertProjects(projectsToSeed)
    //     seeder.projects = ArrayList(db.projectDao().getAllProjects())
    //
    //     val seedCount = projectsToSeed.size
    //     val insertedCount = insertedIds.size
    //
    //     if (insertedCount == seedCount) {
    //         Log.d(LOG_TAG, "Successfully seeded $insertedCount projects!")
    //         return insertedCount
    //     } else {
    //         throw Exception("insertedCount does not match seedCount")
    //     }
    // }

    // @Throws(Exception::class)
    // private fun seedTasks(db: AppDatabase, seeder: DBSeeder) : Int {
    //     val tasksToSeed = seeder.populateTasksList(true)
    //     val insertedIds = db.taskDao().bulkInsertTasks(tasksToSeed)
    //     seeder.tasks = ArrayList(db.taskDao().getAllTasks())
    //
    //     val seedCount = tasksToSeed.size
    //     val insertedCount = insertedIds.size
    //
    //     if (insertedCount == seedCount) {
    //         Log.d(LOG_TAG, "Successfully seeded $insertedCount tasks!")
    //         return insertedCount
    //     } else {
    //         throw Exception("insertedCount does not match seedCount")
    //     }
    // }

    // @Throws(Exception::class)
    // private fun seedTaskTagAssignments(db: AppDatabase, seeder: DBSeeder) : Int {
    //     val taskTagAssignmentsToSeed = seeder.populateTaskTagAssignmentsList()
    //     val insertedIds = db.taskTagAssignmentDao().bulkInsertTaskTagAssignments(taskTagAssignmentsToSeed)
    //     seeder.taskTagAssignments = ArrayList(db.taskTagAssignmentDao().getAllTaskTagAssignments())
    //
    //     val seedCount = taskTagAssignmentsToSeed.size
    //     val insertedCount = insertedIds.size
    //
    //     if (insertedCount == seedCount) {
    //         Log.d(LOG_TAG, "Successfully seeded $insertedCount taskTagAssignments!")
    //         return insertedCount
    //     } else {
    //         throw Exception("insertedCount does not match seedCount")
    //     }
    // }
}