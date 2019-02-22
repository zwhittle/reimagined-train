package com.awesome.zach.jotunheimrsandbox.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.data.entities.TaskTagAssignment

@Dao
interface TaskTagAssignmentDao {

    // returns the id of the inserted row
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTaskTagAssignment(taskTagAssignment: TaskTagAssignment) : Long

    // returns a list of the inserted row ids
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsertTaskTagAssignments(taskTagAssignments: List<TaskTagAssignment>) : List<Long>

    // returns a count of updated rows
    @Update
    fun updateTaskTagAssignment(taskTagAssignment: TaskTagAssignment) : Int

    // returns a count of updated rows
    @Update
    fun bulkUpdateTaskTagAssignments(taskTagAssignments: List<TaskTagAssignment>) : Int

    // returns the count of deleted rows
    @Delete
    fun deleteTaskTagAssignment(taskTagAssignment: TaskTagAssignment) : Int

    // returns the count of deleted rows
    @Delete
    fun bulkDeleteTaskTagAssignments(taskTagAssignments: List<TaskTagAssignment>) : Int

    // returns the count of deleted rows
    @Query("DELETE FROM task_tag_join_table")
    fun deleteAllTaskTagAssignments() : Int

    @Query("SELECT * FROM task_tag_join_table")
    fun getAllTaskTagAssignments() : List<TaskTagAssignment>

    @Query("SELECT * FROM task_tag_join_table WHERE taskTagAssignmentId = :taskTagAssignmentId")
    fun getTaskTagAssignmentById(taskTagAssignmentId: Long) : TaskTagAssignment

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM task_table INNER JOIN task_tag_join_table ON task_table.taskId = task_tag_join_table.taskId WHERE task_tag_join_table.tagId = :tagId")
    fun getTasksWithTagLive(tagId: Long): LiveData<List<Task>>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM tag_table INNER JOIN task_tag_join_table ON tag_table.tagId = task_tag_join_table.tagId WHERE task_tag_join_table.taskId = :taskId")
    fun getTagsForTask(taskId: Long): List<Tag>
}