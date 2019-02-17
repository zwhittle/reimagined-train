package com.awesome.zach.jotunheimrsandbox.db.daos

import android.arch.persistence.room.*
import com.awesome.zach.jotunheimrsandbox.db.entities.Task
import java.util.*

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

    // returns the inserted row id
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task) : Long

    // returns a list of inserted row ids
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTasks(tasks: List<Task>) : List<Long>

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

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): List<Task>

    @Query("SELECT * FROM task_table WHERE taskId == :taskId")
    fun getTaskById(taskId: Long): Task

    @Query("SELECT * FROM task_table WHERE name == :name")
    fun getTasksByName(name: String): List<Task>

    @Query("SELECT * FROM task_table WHERE date_start == :startDate")
    fun getTasksByStartDate(startDate: Date): List<Task>

    @Query("SELECT * FROM task_table WHERE date_end == :endDate")
    fun getTasksByEndDate(endDate: Date): List<Task>

    @Query("SELECT * FROM task_table WHERE projectId == :projectId")
    fun getTasksByProject(projectId: Long): List<Task>
}