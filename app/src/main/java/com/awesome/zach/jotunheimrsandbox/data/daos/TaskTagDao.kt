package com.awesome.zach.jotunheimrsandbox.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.data.entities.TaskTag

@Dao
interface TaskTagDao {

    @Query("SELECT count(id) FROM task_tag")
    fun count(): Int

    @Query("SELECT count(id) FROM task_tag")
    fun has(): Boolean

    @Query("SELECT * FROM task_tag WHERE id = :id")
    fun taskTagByIdNow(id: Long): TaskTag?

    @Query("SELECT * FROM task INNER JOIN task_tag ON task.id = task_tag.taskId WHERE task_tag.tagId = :tagId")
    fun tasksWithTag(tagId: Long): LiveData<List<Task>>

    @Query("SELECT * FROM tag INNER JOIN task_tag ON tag.id = task_tag.taskId WHERE task_tag.taskId = :taskId")
    fun tagsForTask(taskId: Long): LiveData<List<Tag>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg taskTag: TaskTag): Array<Long>

    @Query("DELETE FROM task_tag WHERE id = :id")
    fun deleteById(id: Long): Int

    @Delete
    fun delete(taskTag: TaskTag): Int

    @Query("DELETE FROM task_tag")
    fun deleteAll(): Int

    /** Old methods below */
    
    // returns the id of the inserted row
    // @Insert(onConflict = OnConflictStrategy.REPLACE)
    // fun insertTaskTagAssignment(taskTagAssignment: TaskTag) : Long
    //
    // // returns a list of the inserted row ids
    // @Insert(onConflict = OnConflictStrategy.REPLACE)
    // fun bulkInsertTaskTagAssignments(taskTagAssignments: List<TaskTag>) : List<Long>
    //
    // // returns a count of updated rows
    // @Update
    // fun updateTaskTagAssignment(taskTagAssignment: TaskTag) : Int
    //
    // // returns a count of updated rows
    // @Update
    // fun bulkUpdateTaskTagAssignments(taskTagAssignments: List<TaskTag>) : Int
    //
    // // returns the count of deleted rows
    // @Delete
    // fun deleteTaskTagAssignment(taskTagAssignment: TaskTag) : Int
    //
    // // returns the count of deleted rows
    // @Delete
    // fun bulkDeleteTaskTagAssignments(taskTagAssignments: List<TaskTag>) : Int
    //
    // // returns the count of deleted rows
    // @Query("DELETE FROM task_tag")
    // fun deleteAllTaskTagAssignments() : Int
    //
    // @Query("SELECT * FROM task_tag")
    // fun getAllTaskTagAssignments() : List<TaskTag>
    //
    // @Query("SELECT * FROM task_tag WHERE id = :id")
    // fun getTaskTagAssignmentById(id: Long) : TaskTag
    //
    // @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    // @Query("SELECT * FROM task INNER JOIN task_tag ON task.id = task_tag.id WHERE task_tag.id = :id")
    // fun getTasksWithTagLive(id: Long): LiveData<List<Task>>
    //
    // @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    // @Query("SELECT * FROM task INNER JOIN task_tag ON task.id = task_tag.id WHERE task_tag.id = :id AND task.completed == 0")
    // fun getActiveTasksWithTagLive(id: Long): LiveData<List<Task>>
    //
    // @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    // @Query("SELECT * FROM tag INNER JOIN task_tag ON tag.id = task_tag.id WHERE task_tag.id = :id")
    // fun getTagsForTask(id: Long): List<Tag>
}