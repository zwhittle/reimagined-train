package com.awesome.zach.jotunheimrsandbox.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.data.entities.TaskTag

@Dao
interface TaskTagAssignmentDao {

    // returns the id of the inserted row
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTaskTagAssignment(taskTagAssignment: TaskTag) : Long

    // returns a list of the inserted row ids
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsertTaskTagAssignments(taskTagAssignments: List<TaskTag>) : List<Long>

    // returns a count of updated rows
    @Update
    fun updateTaskTagAssignment(taskTagAssignment: TaskTag) : Int

    // returns a count of updated rows
    @Update
    fun bulkUpdateTaskTagAssignments(taskTagAssignments: List<TaskTag>) : Int

    // returns the count of deleted rows
    @Delete
    fun deleteTaskTagAssignment(taskTagAssignment: TaskTag) : Int

    // returns the count of deleted rows
    @Delete
    fun bulkDeleteTaskTagAssignments(taskTagAssignments: List<TaskTag>) : Int

    // returns the count of deleted rows
    @Query("DELETE FROM task_tag_table")
    fun deleteAllTaskTagAssignments() : Int

    @Query("SELECT * FROM task_tag_table")
    fun getAllTaskTagAssignments() : List<TaskTag>

    @Query("SELECT * FROM task_tag_table WHERE taskTagId = :taskTagId")
    fun getTaskTagAssignmentById(taskTagId: Long) : TaskTag

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM task_table INNER JOIN task_tag_table ON task_table.id = task_tag_table.id WHERE task_tag_table.id = :id")
    fun getTasksWithTagLive(tagId: Long): LiveData<List<Task>>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM task_table INNER JOIN task_tag_table ON task_table.id = task_tag_table.id WHERE task_tag_table.id = :id AND task_table.completed == 0")
    fun getActiveTasksWithTagLive(tagId: Long): LiveData<List<Task>>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM tag_table INNER JOIN task_tag_table ON tag_table.id = task_tag_table.id WHERE task_tag_table.id = :id")
    fun getTagsForTask(taskId: Long): List<Tag>
}