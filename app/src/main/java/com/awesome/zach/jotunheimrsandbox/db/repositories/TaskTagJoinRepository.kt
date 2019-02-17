package com.awesome.zach.jotunheimrsandbox.db.repositories

import com.awesome.zach.jotunheimrsandbox.db.daos.TaskTagJoinDao
import com.awesome.zach.jotunheimrsandbox.db.entities.TaskTagJoin
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class TaskTagJoinRepository private constructor(
    private val taskTagJoinDao: TaskTagJoinDao
){

    suspend fun insertTaskTagJoin(taskTagJoin: TaskTagJoin) : Long {
        return withContext(IO) {
            taskTagJoinDao.insertTaskTagJoin(taskTagJoin)
        }
    }

    suspend fun bulkInsertTaskTagJoin(taskTagJoins: List<TaskTagJoin>) : List<Long> {
        return withContext(IO) {
            taskTagJoinDao.bulkInsertTaskTagJoins(taskTagJoins)
        }
    }

    suspend fun updateTaskTagJoin(taskTagJoin: TaskTagJoin) : Int {
        return withContext(IO) {
            taskTagJoinDao.updateTaskTagJoin(taskTagJoin)
        }
    }

    suspend fun bulkUpdateTaskTagJoin(taskTagJoins: List<TaskTagJoin>) : Int {
        return withContext(IO) {
            taskTagJoinDao.bulkUpdateTaskTagJoins(taskTagJoins)
        }
    }

    suspend fun deleteTaskTagJoin(taskTagJoin: TaskTagJoin) : Int {
        return withContext(IO) {
            taskTagJoinDao.deleteTaskTagJoin(taskTagJoin)
        }
    }

    suspend fun bulkDeleteTaskTagJoins(taskTagJoins: List<TaskTagJoin>) : Int {
        return withContext(IO) {
            taskTagJoinDao.bulkDeleteTaskTagJoins(taskTagJoins)
        }
    }

    suspend fun deleteAllTaskTagJoins() : Int {
        return withContext(IO) {
            taskTagJoinDao.deleteAllTaskTagJoins()
        }
    }

    fun getAllTaskTagJoins() = taskTagJoinDao.getAllTaskTagJoins()

    fun getTasksWithTag(tagId: Long) = taskTagJoinDao.getTasksWithTag(tagId)

    fun getTagsForTask(taskId: Long) = taskTagJoinDao.getTagsForTask(taskId)

    companion object {

        // for singleton instantiation
        @Volatile
        private var instance: TaskTagJoinRepository? = null

        fun getInstance(taskTagJoinDao: TaskTagJoinDao) =
            instance ?: synchronized(this) {
                instance ?: TaskTagJoinRepository(taskTagJoinDao).also { instance = it }
            }
    }
}