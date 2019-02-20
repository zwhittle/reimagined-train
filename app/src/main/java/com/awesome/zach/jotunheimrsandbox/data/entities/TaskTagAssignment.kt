package com.awesome.zach.jotunheimrsandbox.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.annotation.NonNull
import androidx.room.Index

@Entity(tableName = "task_tag_join_table",
        indices = [Index("taskTagAssignmentId"), Index("taskId"), Index("tagId")],
        foreignKeys = [ForeignKey(entity = Task::class,
                                  parentColumns = ["taskId"],
                                  childColumns = ["taskId"]), ForeignKey(entity = Tag::class,
                                                                         parentColumns = ["tagId"],
                                                                         childColumns = ["tagId"])])
data class TaskTagAssignment(
    @PrimaryKey(autoGenerate = true)
    var taskTagAssignmentId: Long = 0,
    @NonNull
    var taskId: Long,
    @NonNull
    var tagId: Long)