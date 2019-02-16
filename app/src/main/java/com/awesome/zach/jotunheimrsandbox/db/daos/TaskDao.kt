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

    // returns the id of the inserted row
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task) : Long

    // returns a count of the updated rows
    @Update
    fun updateTask(task: Task) : Int

    @Delete
    fun deleteTask(task: Task)

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