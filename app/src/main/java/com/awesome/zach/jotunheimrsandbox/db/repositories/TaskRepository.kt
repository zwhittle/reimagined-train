package com.awesome.zach.jotunheimrsandbox.db.repositories

import com.awesome.zach.jotunheimrsandbox.db.daos.TaskDao
import com.awesome.zach.jotunheimrsandbox.db.entities.Task
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.time.LocalDate

class TaskRepository private constructor(
    private val taskDao: TaskDao
){

    suspend fun insertTask(task: Task) : Long {
        return withContext(IO) {
            taskDao.insertTask(task)
        }
    }

    suspend fun bulkInsertTasks(tasks: List<Task>) : List<Long> {
        return withContext(IO) {
            taskDao.bulkInsertTasks(tasks)
        }
    }

    suspend fun updateTask(task: Task) : Int {
        return withContext(IO) {
            taskDao.updateTask(task)
        }
    }

    suspend fun bulkUpdateTasks(tasks: List<Task>) : Int {
        return withContext(IO) {
            taskDao.bulkUpdateTasks(tasks)
        }
    }

    suspend fun deleteTask(task: Task) : Int {
        return withContext(IO) {
            taskDao.deleteTask(task)
        }
    }

    suspend fun bulkDeleteTasks(tasks: List<Task>) : Int {
        return withContext(IO) {
            taskDao.bulkDeleteTasks(tasks)
        }
    }

    suspend fun deleteAllTasks() : Int {
        return withContext(IO) {
            taskDao.deleteAllTasks()
        }
    }

    fun getAllTasks() = taskDao.getAllTasks()

    fun getTaskById(taskId: Long) = taskDao.getTaskById(taskId)

    fun getTaskByProject(projectId: Long) = taskDao.getTasksByProject(projectId)

    fun getTasksByName(name: String) = taskDao.getTasksByName(name)

    fun getTasksByStartDate(startDate: LocalDate) = taskDao.getTasksByStartDate(startDate)

    fun getTasksByEndDate(endDate: LocalDate) = taskDao.getTasksByEndDate(endDate)

//    fun getTasksDueThisWeek(locale: Locale = Locale.US) =
//        taskDao.getTasksInRange("date_end", Utils.firstDayOfThisWeek(locale), Utils.lastDayOfThisWeek(locale))
//
//    fun getTasksStartingThisWeek(locale: Locale = Locale.US) =
//        taskDao.getTasksInRange("date_start", Utils.firstDayOfThisWeek(locale), Utils.lastDayOfThisWeek(locale))

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