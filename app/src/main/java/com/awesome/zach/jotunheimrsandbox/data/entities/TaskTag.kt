package com.awesome.zach.jotunheimrsandbox.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "task_tag",
        indices = [Index("id"), Index("taskId"), Index("tagId")],
        foreignKeys = [
            ForeignKey(
                entity = Task::class,
                parentColumns = ["id"],
                childColumns = ["taskId"],
                onDelete = ForeignKey.CASCADE),
            ForeignKey(
                entity = Tag::class,
                parentColumns = ["id"],
                childColumns = ["tagId"],
                onDelete = ForeignKey.CASCADE)])
data class TaskTag(
    var taskId: Long,
    var tagId: Long) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}