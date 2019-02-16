package com.awesome.zach.jotunheimrsandbox.db.daos

import android.arch.persistence.room.*
import com.awesome.zach.jotunheimrsandbox.db.entities.Tag
import com.awesome.zach.jotunheimrsandbox.db.entities.Task
import com.awesome.zach.jotunheimrsandbox.db.entities.TaskTagJoin

@Dao
interface TaskTagJoinDao {

    // returns the id of the inserted row
    @Insert
    fun insertTaskTagJoin(taskTagJoin: TaskTagJoin) : Long

    // returns a count of updated rows
    @Update
    fun updateTaskTagJoin(taskTagJoin: TaskTagJoin) : Int

    @Delete
    fun deleteTaskTagJoin(taskTagJoin: TaskTagJoin)

    @Query("SELECT * FROM task_tag_join_table")
    fun getAllTaskTagJoins() : List<TaskTagJoin>

    @Query("SELECT * FROM task_table INNER JOIN task_tag_join_table ON task_table.taskId = task_tag_join_table.taskId WHERE task_tag_join_table.tagId = :tagId")
    fun getTasksWithTag(tagId: Long): List<Task>

    @Query("SELECT * FROM tag_table INNER JOIN task_tag_join_table ON tag_table.tagId = task_tag_join_table.tagId WHERE task_tag_join_table.taskId = :taskId")
    fun getTagsForTask(taskId: Long): List<Tag>
}