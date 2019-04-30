package com.awesome.zach.jotunheimrsandbox.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.awesome.zach.jotunheimrsandbox.AppDatabase
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.data.entities.TaskTag
import kotlinx.coroutines.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

interface TaskRepository {
    suspend fun newAsync(task: Task): Deferred<Task>
    suspend fun newTaskTagAsync(taskId: Long, tagId: Long): Deferred<TaskTag>
    suspend fun delete(task: Task)
    suspend fun delete(id: Long)
    fun task(): LiveData<Task?>
    fun tasks(): LiveData<List<Task>>
    fun tasksByProject(projectId: Long?): LiveData<List<Task>>
    fun tasksByList(listId: Long?): LiveData<List<Task>>
    fun tasksByTag(tagId: Long?): LiveData<List<Task>>
    fun tasksNowAsync(): Deferred<List<Task>>
}

class TaskRepositoryImpl: TaskRepository, KoinComponent {

    private val db by inject<AppDatabase>()
    private val dao = db.getDatabase().taskDao()

    private val tasks = dao.list()

    private val taskIdLiveData = MutableLiveData<Long>()
    private val taskLiveData = Transformations.switchMap(db.isDatabaseCreated()) { created ->
        if (created) {
            Transformations.switchMap(taskIdLiveData) { id ->
                dao.taskById(id)
            }
        } else {
            MutableLiveData()
        }
    }

    override suspend fun newAsync(task: Task): Deferred<Task> {
        return GlobalScope.async(Dispatchers.IO) {
            val ids = dao.insertAll(task)
            return@async dao.taskByIdNow(ids.first()) ?: throw RuntimeException("Task cannot be null")
        }
    }

    override suspend fun newTaskTagAsync(taskId: Long, tagId: Long): Deferred<TaskTag> {
        return GlobalScope.async(Dispatchers.IO) {
            val ttDao = db.getDatabase().taskTagDao()
            val ids = ttDao.insertAll(TaskTag(taskId, tagId))
            return@async ttDao.taskTagByIdNow(ids.first()) ?: throw java.lang.RuntimeException("TaskTag cannot be null")
        }
    }

    override suspend fun delete(task: Task) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.delete(task)
        }
    }

    override suspend fun delete(id: Long) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

    override fun tasks(): LiveData<List<Task>> = tasks

    override fun tasksByProject(projectId: Long?): LiveData<List<Task>> = dao.listByProject(projectId)

    override fun tasksByList(listId: Long?): LiveData<List<Task>> = dao.listByList(listId)

    override fun tasksByTag(tagId: Long?): LiveData<List<Task>> = dao.listByTag(tagId)

    override fun tasksNowAsync(): Deferred<List<Task>> {
        return GlobalScope.async(Dispatchers.IO) {
            dao.listNow()
        }
    }

    override fun task(): LiveData<Task?> = taskLiveData
}

// import com.awesome.zach.jotunheimrsandbox.data.daos.TaskDao
// import com.awesome.zach.jotunheimrsandbox.data.entities.Task
// import com.awesome.zach.jotunheimrsandbox.utils.Utils
// import kotlinx.coroutines.Dispatchers.IO
// import kotlinx.coroutines.withContext
// import java.time.LocalDate
// import java.util.*
//
// class TaskRepository private constructor(private val taskDao: TaskDao) {
//
//     suspend fun insertTask(task: Task): Long {
//         return withContext(IO) {
//             taskDao.insertTask(task)
//         }
//     }
//
//     suspend fun bulkInsertTasks(tasks: List<Task>): List<Long> {
//         return withContext(IO) {
//             taskDao.bulkInsertTasks(tasks)
//         }
//     }
//
//     suspend fun updateTask(task: Task): Int {
//         return withContext(IO) {
//             taskDao.updateTask(task)
//         }
//     }
//
//     suspend fun bulkUpdateTasks(tasks: List<Task>): Int {
//         return withContext(IO) {
//             taskDao.bulkUpdateTasks(tasks)
//         }
//     }
//
//     suspend fun deleteTask(task: Task): Int {
//         return withContext(IO) {
//             taskDao.deleteTask(task)
//         }
//     }
//
//     suspend fun bulkDeleteTasks(tasks: List<Task>): Int {
//         return withContext(IO) {
//             taskDao.bulkDeleteTasks(tasks)
//         }
//     }
//
//     suspend fun deleteAllTasks(): Int {
//         return withContext(IO) {
//             taskDao.deleteAllTasks()
//         }
//     }
//
//     fun getAllTasks() = taskDao.getAllTasks()
//
//     fun getAllTasksLive() = taskDao.getAllTasksLive()
//
//     fun getActiveTasks() = taskDao.getActiveTasks()
//
//     fun getActiveTasksLive() = taskDao.getActiveTasksLive()
//
//     fun getTaskById(taskId: Long) = taskDao.getTaskById(taskId)
//
//     fun getTasksForProjectLive(projectId: Long) = taskDao.getTasksForProjectLive(projectId)
//
//     fun getTasksOnListLive(listId: Long) = taskDao.getTasksOnListLive(listId)
//
//     fun getInboxTasksLive() = taskDao.getInboxTasksLive()
//
//     fun getTasksWithName(name: String) = taskDao.getTasksWithName(name)
//
//     fun getTasksWithStartDate(startDate: LocalDate) = taskDao.getTasksWithStartDate(startDate)
//
//     fun getTasksWithEndDate(endDate: LocalDate) = taskDao.getTasksWithEndDate(endDate)
//
//     fun getOverdueTasks() = taskDao.getOverdueTasks()
//
//     fun getInProgressTasks() = taskDao.getInProgressTasks()
//
//     fun getTasksDueToday() = taskDao.getTasksDueToday()
//
//     fun getTasksStartingToday() = taskDao.getTasksStartingToday()
//
//     fun getTasksDueThisWeek(locale: Locale = Locale.US) = taskDao.getTasksDueInRange(Utils.firstDayOfThisWeek(locale),
//                                                                                      Utils.lastDayOfThisWeek(locale))
//
//     fun getTasksStartingThisWeek(locale: Locale = Locale.US) = taskDao.getTasksStartingInRange(Utils.firstDayOfThisWeek(locale),
//                                                                                                Utils.lastDayOfThisWeek(locale))
//
//     companion object {
//
//         // for singleton instantiation
//         @Volatile
//         private var instance: TaskRepository? = null
//
//         fun getInstance(taskDao: TaskDao) = instance ?: synchronized(this) {
//             instance ?: TaskRepository(taskDao).also { instance = it }
//         }
//     }
// }