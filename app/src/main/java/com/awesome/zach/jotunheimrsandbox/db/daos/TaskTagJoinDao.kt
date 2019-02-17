package com.awesome.zach.jotunheimrsandbox.db.daos

import android.arch.persistence.room.*
import com.awesome.zach.jotunheimrsandbox.db.entities.Tag
import com.awesome.zach.jotunheimrsandbox.db.entities.Task
import com.awesome.zach.jotunheimrsandbox.db.entities.TaskTagJoin

@Dao
interface TaskTagJoinDao {

    // returns the id of the inserted row
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTaskTagJoin(taskTagJoin: TaskTagJoin) : Long

    // returns a list of the inserted row ids
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsertTaskTagJoins(taskTagJoins: List<TaskTagJoin>) : List<Long>

    // returns a count of updated rows
    @Update
    fun updateTaskTagJoin(taskTagJoin: TaskTagJoin) : Int

    // returns a count of updated rows
    @Update
    fun bulkUpdateTaskTagJoins(taskTagJoins: List<TaskTagJoin>) : Int

    // returns the count of deleted rows
    @Delete
    fun deleteTaskTagJoin(taskTagJoin: TaskTagJoin) : Int

    // returns the count of deleted rows
    @Delete
    fun bulkDeleteTaskTagJoins(taskTagJoins: List<TaskTagJoin>) : Int

    // returns the count of deleted rows
    @Query("DELETE FROM task_tag_join_table")
    fun deleteAllTaskTagJoins() : Int

    @Query("SELECT * FROM task_tag_join_table")
    fun getAllTaskTagJoins() : List<TaskTagJoin>

    @Query("SELECT * FROM task_tag_join_table WHERE taskTagJoinId = :taskTagJoinId")
    fun getTaskTagJoinById(taskTagJoinId: Long) : TaskTagJoin

    @Query("SELECT * FROM task_table INNER JOIN task_tag_join_table ON task_table.taskId = task_tag_join_table.taskId WHERE task_tag_join_table.tagId = :tagId")
    fun getTasksWithTag(tagId: Long): List<Task>

    @Query("SELECT * FROM tag_table INNER JOIN task_tag_join_table ON tag_table.tagId = task_tag_join_table.tagId WHERE task_tag_join_table.taskId = :taskId")
    fun getTagsForTask(taskId: Long): List<Tag>
}