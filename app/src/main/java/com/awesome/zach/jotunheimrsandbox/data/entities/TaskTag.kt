package com.awesome.zach.jotunheimrsandbox.data.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "task_tag_table",
        indices = [Index("taskTagId"), Index("id"), Index("id")],
        foreignKeys = [
            ForeignKey(
                entity = Task::class,
                parentColumns = ["id"],
                childColumns = ["id"],
                onDelete = ForeignKey.CASCADE),
            ForeignKey(
                entity = Tag::class,
                parentColumns = ["id"],
                childColumns = ["id"],
                onDelete = ForeignKey.CASCADE)])
data class TaskTag(
    @NonNull
    var taskId: Long,
    @NonNull
    var tagId: Long) {

    @PrimaryKey(autoGenerate = true)
    var taskTagId: Long = 0
}