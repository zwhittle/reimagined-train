package com.awesome.zach.jotunheimrsandbox.data.repositories

import androidx.lifecycle.LiveData
import com.awesome.zach.jotunheimrsandbox.AppDatabase
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.data.entities.TaskTag
import kotlinx.coroutines.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

interface TaskTagRepository {
    suspend fun newAsync(taskId: Long, tagId: Long): Deferred<TaskTag>
    suspend fun delete(taskTag: TaskTag)
    suspend fun delete(id: Long)
    fun tasksWithTag(tagId: Long): LiveData<List<Task>>
    fun tagsForTask(taskId: Long): LiveData<List<Tag>>
}

class TaskTagRepositoryImpl: TaskTagRepository, KoinComponent {

    private val db by inject<AppDatabase>()
    private val dao = db.getDatabase().taskTagDao()

    override suspend fun newAsync(taskId: Long,
                                  tagId: Long): Deferred<TaskTag> {
        return GlobalScope.async(Dispatchers.IO) {
            val ids = dao.insertAll(TaskTag(taskId, tagId))
            return@async dao.taskTagByIdNow(ids.first()) ?: throw RuntimeException("TaskTag cannot be null")
        }
    }

    override suspend fun delete(taskTag: TaskTag) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.delete(taskTag)
        }
    }

    override suspend fun delete(id: Long) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

    override fun tasksWithTag(tagId: Long): LiveData<List<Task>> = dao.tasksWithTag(tagId)

    override fun tagsForTask(taskId: Long): LiveData<List<Tag>> = dao.tagsForTask(taskId)
}

// import com.awesome.zach.jotunheimrsandbox.data.daos.TaskTagAssignmentDao
// import com.awesome.zach.jotunheimrsandbox.data.entities.TaskTag
// import kotlinx.coroutines.Dispatchers.IO
// import kotlinx.coroutines.withContext
//
// class TaskTagRepository private constructor(private val taskTagDao: TaskTagAssignmentDao)
//
//     suspend fun insertTaskTagAssignment(taskTagAssignment: TaskTag): Long {
//         return withContext(IO) {
//             taskTagDao.insertTaskTagAssignment(taskTagAssignment)
//         }
//     }
//
//     suspend fun bulkInsertTaskTagAssignment(taskTagAssignments: List<TaskTag>): List<Long> {
//         return withContext(IO) {
//             taskTagDao.bulkInsertTaskTagAssignments(taskTagAssignments)
//         }
//     }
//
//     suspend fun updateTaskTagAssignment(taskTagAssignment: TaskTag): Int {
//         return withContext(IO) {
//             taskTagDao.updateTaskTagAssignment(taskTagAssignment)
//         }
//     }
//
//     suspend fun bulkUpdateTaskTagAssignment(taskTagAssignments: List<TaskTag>): Int {
//         return withContext(IO) {
//             taskTagDao.bulkUpdateTaskTagAssignments(taskTagAssignments)
//         }
//     }
//
//     suspend fun deleteTaskTagAssignment(taskTagAssignment: TaskTag): Int {
//         return withContext(IO) {
//             taskTagDao.deleteTaskTagAssignment(taskTagAssignment)
//         }
//     }
//
//     suspend fun bulkDeleteTaskTagAssignments(taskTagAssignments: List<TaskTag>): Int {
//         return withContext(IO) {
//             taskTagDao.bulkDeleteTaskTagAssignments(taskTagAssignments)
//         }
//     }
//
//     suspend fun deleteAllTaskTagAssignments(): Int {
//         return withContext(IO) {
//             taskTagDao.deleteAllTaskTagAssignments()
//         }
//     }
//
//     fun getAllTaskTagAssignments() = taskTagDao.getAllTaskTagAssignments()
//
//     fun getActiveTasksWithTagLive(tagid: Long) = taskTagDao.getActiveTasksWithTagLive(tagid)
//
//     fun getTasksWithTagLive(tagId: Long) = taskTagDao.getTasksWithTagLive(tagId)
//
//     fun getTagsForTask(taskId: Long) = taskTagDao.getTagsForTask(taskId)
//
//     companion object {
//
//         // for singleton instantiation
//         @Volatile
//         private var instance: TaskTagRepository? = null
//
//         fun getInstance(taskTagDao: TaskTagAssignmentDao) =
//             instance ?: synchronized(this) {
//                 instance ?: TaskTagRepository(taskTagDao).also { instance = it }
//             }
//     }
// }