package com.awesome.zach.jotunheimrsandbox.data.repositories

import com.awesome.zach.jotunheimrsandbox.data.daos.TaskTagAssignmentDao
import com.awesome.zach.jotunheimrsandbox.data.entities.TaskTag
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class TaskTagAssignmentRepository private constructor(private val taskTagAssignmentDao: TaskTagAssignmentDao) {

    suspend fun insertTaskTagAssignment(taskTagAssignment: TaskTag): Long {
        return withContext(IO) {
            taskTagAssignmentDao.insertTaskTagAssignment(taskTagAssignment)
        }
    }

    suspend fun bulkInsertTaskTagAssignment(taskTagAssignments: List<TaskTag>): List<Long> {
        return withContext(IO) {
            taskTagAssignmentDao.bulkInsertTaskTagAssignments(taskTagAssignments)
        }
    }

    suspend fun updateTaskTagAssignment(taskTagAssignment: TaskTag): Int {
        return withContext(IO) {
            taskTagAssignmentDao.updateTaskTagAssignment(taskTagAssignment)
        }
    }

    suspend fun bulkUpdateTaskTagAssignment(taskTagAssignments: List<TaskTag>): Int {
        return withContext(IO) {
            taskTagAssignmentDao.bulkUpdateTaskTagAssignments(taskTagAssignments)
        }
    }

    suspend fun deleteTaskTagAssignment(taskTagAssignment: TaskTag): Int {
        return withContext(IO) {
            taskTagAssignmentDao.deleteTaskTagAssignment(taskTagAssignment)
        }
    }

    suspend fun bulkDeleteTaskTagAssignments(taskTagAssignments: List<TaskTag>): Int {
        return withContext(IO) {
            taskTagAssignmentDao.bulkDeleteTaskTagAssignments(taskTagAssignments)
        }
    }

    suspend fun deleteAllTaskTagAssignments(): Int {
        return withContext(IO) {
            taskTagAssignmentDao.deleteAllTaskTagAssignments()
        }
    }

    fun getAllTaskTagAssignments() = taskTagAssignmentDao.getAllTaskTagAssignments()

    fun getActiveTasksWithTagLive(tagid: Long) = taskTagAssignmentDao.getActiveTasksWithTagLive(tagid)

    fun getTasksWithTagLive(tagId: Long) = taskTagAssignmentDao.getTasksWithTagLive(tagId)

    fun getTagsForTask(taskId: Long) = taskTagAssignmentDao.getTagsForTask(taskId)

    companion object {

        // for singleton instantiation
        @Volatile
        private var instance: TaskTagAssignmentRepository? = null

        fun getInstance(taskTagAssignmentDao: TaskTagAssignmentDao) =
            instance ?: synchronized(this) {
                instance ?: TaskTagAssignmentRepository(taskTagAssignmentDao).also { instance = it }
            }
    }
}