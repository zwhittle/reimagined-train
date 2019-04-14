package com.awesome.zach.jotunheimrsandbox.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import java.time.LocalDate

/**
 * Task @Dao
 *
 * Uses @Insert, @Update, and @Delete convenience functions
 *
 * Single Result Queries:
 * getTaskById()
 *
 * Multi Result Queries:
 * getAllTasks()
 * getTasksWithName()
 * getTasksWithStartDate()
 * getTasksWithEndDate()
 * getTasksForProject()
 *
 */

@Dao
interface TaskDao {

    // returns the inserted row id
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task): Long

    // returns a list of inserted row ids
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsertTasks(tasks: List<Task>): List<Long>

    // returns a count of the updated rows
    @Update
    fun updateTask(task: Task): Int

    // returns a count of the updated rows
    @Update
    fun bulkUpdateTasks(tasks: List<Task>): Int

    // returns the count of deleted rows
    @Delete
    fun deleteTask(task: Task): Int

    // returns the count of deleted rows
    @Delete
    fun bulkDeleteTasks(tasks: List<Task>): Int

    // returns the count of deleted rows
    @Query("DELETE FROM task_table")
    fun deleteAllTasks(): Int

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId ORDER BY date_end ASC")
    fun getAllTasks(): List<Task>

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId ORDER BY date_end ASC")
    fun getAllTasksLive(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId WHERE completed == 0 ORDER BY date_end ASC")
    fun getActiveTasks(): List<Task>

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId WHERE completed == 0 ORDER BY date_end ASC")
    fun getActiveTasksLive(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId WHERE taskId == :taskId ORDER BY date_end ASC")
    fun getTaskById(taskId: Long): Task

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId WHERE completed == :completed AND task_table.listId == :listId ORDER BY date_end ASC")
    fun getTasksOnListLive(listId: Long, completed: Boolean = false): LiveData<List<Task>>

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId WHERE completed == :completed AND task_table.listId == :listId ORDER BY date_end ASC")
    fun getTasksOnList(listId: Long, completed: Boolean = false): List<Task>

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId WHERE completed == :completed AND taskName == :name ORDER BY date_end ASC")
    fun getTasksWithName(name: String,
                         completed: Boolean = false): List<Task>

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId WHERE completed == :completed AND date_start == :startDate ORDER BY date_end ASC")
    fun getTasksWithStartDate(startDate: LocalDate,
                              completed: Boolean = false): List<Task>

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId WHERE completed == :completed AND date_start == :today ORDER BY taskName ASC")
    fun getTasksStartingToday(today: String = LocalDate.now().toString(),
                              completed: Boolean = false): List<Task>

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId WHERE completed == :completed AND date_end == :endDate ORDER BY date_end ASC")
    fun getTasksWithEndDate(endDate: LocalDate,
                            completed: Boolean = false): List<Task>

    // convenience method for passing in Today as a param into the above
    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId WHERE completed == :completed AND date_end == :today ORDER BY taskName ASC")
    fun getTasksDueToday(today: String = LocalDate.now().toString(),
                         completed: Boolean = false): List<Task>

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId WHERE completed == :completed AND task_table.projectId == :projectId ORDER BY date_end ASC")
    fun getTasksForProject(projectId: Long,
                               completed: Boolean = false): List<Task>

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId WHERE completed == :completed AND task_table.projectId == :projectId ORDER BY date_end ASC")
    fun getTasksForProjectLive(projectId: Long,
                           completed: Boolean = false): LiveData<List<Task>>

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId WHERE completed == :completed AND task_table.projectId IS NULL ORDER BY date_end ASC")
    fun getInboxTasksLive(completed: Boolean = false): LiveData<List<Task>>

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId WHERE completed == :completed AND date_end BETWEEN :rangeStart AND :rangeEnd ORDER BY date_end ASC")
    fun getTasksDueInRange(rangeStart: LocalDate,
                           rangeEnd: LocalDate,
                           completed: Boolean = false): List<Task>

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId WHERE completed == :completed AND date_start BETWEEN :rangeStart AND :rangeEnd ORDER BY date_start ASC")
    fun getTasksStartingInRange(rangeStart: LocalDate,
                                rangeEnd: LocalDate,
                                completed: Boolean = false): List<Task>

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId WHERE completed == 0 AND date_end < :today ORDER BY date_end ASC")
    fun getOverdueTasks(today: String = LocalDate.now().toString()): List<Task>

    @Query("SELECT * FROM task_table LEFT JOIN project_table ON task_table.projectId = project_table.projectId LEFT JOIN list_table ON task_table.listId = list_table.listId WHERE completed == 0 AND date_start < :today ORDER BY date_end ASC")
    fun getInProgressTasks(today: String = LocalDate.now().toString()): List<Task>
}