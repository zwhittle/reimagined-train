package com.awesome.zach.jotunheimrsandbox.db.repositories

import com.awesome.zach.jotunheimrsandbox.db.daos.TaskTagJoinDao
import com.awesome.zach.jotunheimrsandbox.db.entities.TaskTagJoin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskTagJoinRepository private constructor(
    private val taskTagJoinDao: TaskTagJoinDao
){

    suspend fun createTaskTagJoin(taskId: Long, tagId: Long) {
        withContext(Dispatchers.IO) {
            val taskTagJoin = TaskTagJoin(taskId = taskId, tagId = tagId)
            val id = taskTagJoinDao.insertTaskTagJoin(taskTagJoin)
        }
    }

    suspend fun updateTaskTagJoin(taskTagJoin: TaskTagJoin) {
        withContext(Dispatchers.IO) {
            val count = taskTagJoinDao.updateTaskTagJoin(taskTagJoin)
        }
    }

    suspend fun deleteTaskTagJoin(taskTagJoin: TaskTagJoin) {
        withContext(Dispatchers.IO) {
            taskTagJoinDao.deleteTaskTagJoin(taskTagJoin)
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