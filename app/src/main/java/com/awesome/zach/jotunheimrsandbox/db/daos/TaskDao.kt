package com.awesome.zach.jotunheimrsandbox.db.daos

import android.arch.persistence.room.*
import com.awesome.zach.jotunheimrsandbox.db.entities.Task
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
 * getTasksByName()
 * getTasksByStartDate()
 * getTasksByEndDate()
 * getTasksByProject()
 *
 */

@Dao
interface TaskDao {

    /**
     * TODO: How should I handle grabbing complete vs incomplete tasks?
     */

    // returns the inserted row id
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task) : Long

    // returns a list of inserted row ids
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsertTasks(tasks: List<Task>) : List<Long>

    // returns a count of the updated rows
    @Update
    fun updateTask(task: Task) : Int

    // returns a count of the updated rows
    @Update
    fun bulkUpdateTasks(tasks: List<Task>) : Int

    // returns the count of deleted rows
    @Delete
    fun deleteTask(task: Task) : Int

    // returns the count of deleted rows
    @Delete
    fun bulkDeleteTasks(tasks: List<Task>) : Int

    // returns the count of deleted rows
    @Query("DELETE FROM task_table")
    fun deleteAllTasks() : Int

    @Query("SELECT * FROM task_table ORDER BY name ASC")
    fun getAllTasks(): List<Task>

    @Query("SELECT * FROM task_table WHERE taskId == :taskId ORDER BY name ASC")
    fun getTaskById(taskId: Long): Task

    @Query("SELECT * FROM task_table WHERE name == :name ORDER BY name ASC")
    fun getTasksByName(name: String): List<Task>

    @Query("SELECT * FROM task_table WHERE date_start == :startDate ORDER BY name ASC")
    fun getTasksByStartDate(startDate: LocalDate): List<Task>

    @Query("SELECT * FROM task_table WHERE date_end == :endDate ORDER BY name ASC")
    fun getTasksByEndDate(endDate: LocalDate): List<Task>

    @Query("SELECT * FROM task_table WHERE projectId == :projectId ORDER BY name ASC")
    fun getTasksByProject(projectId: Long): List<Task>

    @Query("SELECT * FROM task_table WHERE date_end BETWEEN :rangeStart AND :rangeEnd ORDER BY name ASC")
    fun getTasksDueInRange(rangeStart: LocalDate, rangeEnd: LocalDate) : List<Task>

    @Query("SELECT * FROM task_table WHERE date_start BETWEEN :rangeStart AND :rangeEnd ORDER BY name ASC")
    fun getTasksStartingInRange(rangeStart: LocalDate, rangeEnd: LocalDate) : List<Task>

    @Query("SELECT * FROM task_table WHERE completed == 1 AND date_end < :today ORDER BY name ASC")
    fun getOverdueTasks(today: String = LocalDate.now().toString()) : List<Task>
}