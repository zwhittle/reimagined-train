package com.awesome.zach.jotunheimrsandbox.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.awesome.zach.jotunheimrsandbox.data.entities.Task

@Dao
interface TaskDao {

    @Query("SELECT count(id) FROM task")
    fun count(): Int

    @Query("SELECT count(id) FROM task")
    fun has(): Boolean

    @Query("SELECT * FROM task WHERE id = :id")
    fun taskByIdNow(id: Long): Task?

    @Query("SELECT * FROM task WHERE id = :id")
    fun taskById(id: Long): LiveData<Task?>

    @Query("SELECT task.id, task.name, task.date_end, task.completed, task.priority, task.projectId, task.listId, task.createdAt, project.name as projectName, list.name as listName FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == 0 ORDER BY task.createdAt DESC")
    fun list(): LiveData<List<Task>>

    @Query("SELECT task.id, task.name, task.date_end, task.completed, task.priority, task.projectId, task.listId, task.createdAt, project.name as projectName, list.name as listName FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == 0 AND task.projectId == :projectId ORDER BY task.createdAt DESC")
    fun listByProject(projectId: Long?): LiveData<List<Task>>

    @Query("SELECT task.id, task.name, task.date_end, task.completed, task.priority, task.projectId, task.listId, task.createdAt, project.name as projectName, list.name as listName FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == 0 AND task.listId == :listId ORDER BY task.createdAt DESC")
    fun listByList(listId: Long?): LiveData<List<Task>>

    @Query("SELECT * FROM task INNER JOIN task_tag ON task.id = task_tag.taskId WHERE task_tag.tagId = :tagId")
    fun listByTag(tagId: Long?): LiveData<List<Task>>

    @Query("SELECT * FROM task ORDER BY createdAt DESC")
    fun listNow(): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg task: Task): Array<Long>

    @Query("DELETE FROM task WHERE id = :id")
    fun deleteById(id: Long): Int

    @Delete
    fun delete(task: Task): Int

    @Query("DELETE FROM task")
    fun deleteAll(): Int

    /** Old methods below */

    // returns the inserted row id
    // @Insert(onConflict = OnConflictStrategy.REPLACE)
    // fun insertTask(task: Task): Long
    //
    // // returns a list of inserted row ids
    // @Insert(onConflict = OnConflictStrategy.REPLACE)
    // fun bulkInsertTasks(tasks: List<Task>): List<Long>
    //
    // // returns a count of the updated rows
    // @Update
    // fun updateTask(task: Task): Int
    //
    // // returns a count of the updated rows
    // @Update
    // fun bulkUpdateTasks(tasks: List<Task>): Int
    //
    // // returns the count of deleted rows
    // @Delete
    // fun deleteTask(task: Task): Int
    //
    // // returns the count of deleted rows
    // @Delete
    // fun bulkDeleteTasks(tasks: List<Task>): Int
    //
    // // returns the count of deleted rows
    // @Query("DELETE FROM task")
    // fun deleteAllTasks(): Int
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id ORDER BY date_end ASC")
    // fun getAllTasks(): List<Task>
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id ORDER BY date_end ASC")
    // fun getAllTasksLive(): LiveData<List<Task>>
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == 0 ORDER BY date_end ASC")
    // fun getActiveTasks(): List<Task>
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == 0 ORDER BY date_end ASC")
    // fun getActiveTasksLive(): LiveData<List<Task>>
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE id == :id ORDER BY date_end ASC")
    // fun getTaskById(id: Long): Task
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == :completed AND task.id == :id ORDER BY date_end ASC")
    // fun getTasksOnListLive(id: Long, completed: Boolean = false): LiveData<List<Task>>
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == :completed AND task.id == :id ORDER BY date_end ASC")
    // fun getTasksOnList(id: Long, completed: Boolean = false): List<Task>
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == :completed AND name == :name ORDER BY date_end ASC")
    // fun getTasksWithName(name: String, completed: Boolean = false): List<Task>
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == :completed AND date_start == :startDate ORDER BY date_end ASC")
    // fun getTasksWithStartDate(startDate: LocalDate, completed: Boolean = false): List<Task>
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == :completed AND date_start == :today ORDER BY name ASC")
    // fun getTasksStartingToday(today: String = LocalDate.now().toString(), completed: Boolean = false): List<Task>
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == :completed AND date_end == :endDate ORDER BY date_end ASC")
    // fun getTasksWithEndDate(endDate: LocalDate, completed: Boolean = false): List<Task>
    //
    // // convenience method for passing in Today as a param into the above
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == :completed AND date_end == :today ORDER BY name ASC")
    // fun getTasksDueToday(today: String = LocalDate.now().toString(), completed: Boolean = false): List<Task>
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == :completed AND task.id == :id ORDER BY date_end ASC")
    // fun getTasksForProject(id: Long, completed: Boolean = false): List<Task>
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == :completed AND task.id == :id ORDER BY date_end ASC")
    // fun getTasksForProjectLive(id: Long, completed: Boolean = false): LiveData<List<Task>>
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == :completed AND task.id IS NULL ORDER BY date_end ASC")
    // fun getInboxTasksLive(completed: Boolean = false): LiveData<List<Task>>
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == :completed AND date_end BETWEEN :rangeStart AND :rangeEnd ORDER BY date_end ASC")
    // fun getTasksDueInRange(rangeStart: LocalDate, rangeEnd: LocalDate, completed: Boolean = false): List<Task>
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == :completed AND date_start BETWEEN :rangeStart AND :rangeEnd ORDER BY date_start ASC")
    // fun getTasksStartingInRange(rangeStart: LocalDate, rangeEnd: LocalDate, completed: Boolean = false): List<Task>
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == 0 AND date_end < :today ORDER BY date_end ASC")
    // fun getOverdueTasks(today: String = LocalDate.now().toString()): List<Task>
    //
    // @Query("SELECT * FROM task LEFT JOIN project ON task.id = project.id LEFT JOIN list ON task.id = list.id WHERE completed == 0 AND date_start < :today ORDER BY date_end ASC")
    // fun getInProgressTasks(today: String = LocalDate.now().toString()): List<Task>
}