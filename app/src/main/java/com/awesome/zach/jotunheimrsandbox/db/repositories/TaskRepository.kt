package com.awesome.zach.jotunheimrsandbox.db.repositories

import com.awesome.zach.jotunheimrsandbox.db.daos.TaskDao
import com.awesome.zach.jotunheimrsandbox.db.entities.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class TaskRepository private constructor(
    private val taskDao: TaskDao
){

    suspend fun createTask(name: String, startDate: Date, endDate: Date, projectId: Long) {
        withContext(Dispatchers.IO) {
            val task = Task(name = name, date_start = startDate, date_end = endDate, projectId = projectId)
            val id = taskDao.insertTask(task)
        }
    }

    suspend fun updatedTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.updateTask(task)
        }
    }

    suspend fun deleteTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.deleteTask(task)
        }
    }

    fun getAllTasks() = taskDao.getAllTasks()

    fun getTaskById(taskId: Long) = taskDao.getTaskById(taskId)

    fun getTaskByProject(projectId: Long) = taskDao.getTasksByProject(projectId)

    fun getTasksByName(name: String) = taskDao.getTasksByName(name)

    fun getTasksByStartDate(startDate: Date) = taskDao.getTasksByStartDate(startDate)

    fun getTasksByEndDate(endDate: Date) = taskDao.getTasksByEndDate(endDate)

    companion object {

        // for singleton instantiation
        @Volatile
        private var instance: TaskRepository? = null

        fun getInstance(taskDao: TaskDao) =
            instance ?: synchronized(this) {
                instance ?: TaskRepository(taskDao).also { instance = it }
            }
    }
}