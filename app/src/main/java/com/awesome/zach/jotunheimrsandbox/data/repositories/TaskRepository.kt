package com.awesome.zach.jotunheimrsandbox.data.repositories

import com.awesome.zach.jotunheimrsandbox.data.daos.TaskDao
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.utils.Utils
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.*

class TaskRepository private constructor(private val taskDao: TaskDao) {

    suspend fun insertTask(task: Task): Long {
        return withContext(IO) {
            taskDao.insertTask(task)
        }
    }

    suspend fun bulkInsertTasks(tasks: List<Task>): List<Long> {
        return withContext(IO) {
            taskDao.bulkInsertTasks(tasks)
        }
    }

    suspend fun updateTask(task: Task): Int {
        return withContext(IO) {
            taskDao.updateTask(task)
        }
    }

    suspend fun bulkUpdateTasks(tasks: List<Task>): Int {
        return withContext(IO) {
            taskDao.bulkUpdateTasks(tasks)
        }
    }

    suspend fun deleteTask(task: Task): Int {
        return withContext(IO) {
            taskDao.deleteTask(task)
        }
    }

    suspend fun bulkDeleteTasks(tasks: List<Task>): Int {
        return withContext(IO) {
            taskDao.bulkDeleteTasks(tasks)
        }
    }

    suspend fun deleteAllTasks(): Int {
        return withContext(IO) {
            taskDao.deleteAllTasks()
        }
    }

    fun getAllTasks() = taskDao.getAllTasks()

    fun getAllTasksLive() = taskDao.getAllTasksLive()

    fun getActiveTasks() = taskDao.getActiveTasks()

    fun getActiveTasksLive() = taskDao.getActiveTasksLive()

    fun getTaskById(taskId: Long) = taskDao.getTaskById(taskId)

    fun getTasksForProjectLive(projectId: Long) = taskDao.getTasksForProjectLive(projectId)

    fun getTasksOnListLive(listId: Long) = taskDao.getTasksOnListLive(listId)

    fun getInboxTasksLive() = taskDao.getInboxTasksLive()

    fun getTasksWithName(name: String) = taskDao.getTasksWithName(name)

    fun getTasksWithStartDate(startDate: LocalDate) = taskDao.getTasksWithStartDate(startDate)

    fun getTasksWithEndDate(endDate: LocalDate) = taskDao.getTasksWithEndDate(endDate)

    fun getOverdueTasks() = taskDao.getOverdueTasks()

    fun getInProgressTasks() = taskDao.getInProgressTasks()

    fun getTasksDueToday() = taskDao.getTasksDueToday()

    fun getTasksStartingToday() = taskDao.getTasksStartingToday()

    fun getTasksDueThisWeek(locale: Locale = Locale.US) = taskDao.getTasksDueInRange(Utils.firstDayOfThisWeek(locale),
                                                                                     Utils.lastDayOfThisWeek(locale))

    fun getTasksStartingThisWeek(locale: Locale = Locale.US) = taskDao.getTasksStartingInRange(Utils.firstDayOfThisWeek(locale),
                                                                                               Utils.lastDayOfThisWeek(locale))

    companion object {

        // for singleton instantiation
        @Volatile
        private var instance: TaskRepository? = null

        fun getInstance(taskDao: TaskDao) = instance ?: synchronized(this) {
            instance ?: TaskRepository(taskDao).also { instance = it }
        }
    }
}