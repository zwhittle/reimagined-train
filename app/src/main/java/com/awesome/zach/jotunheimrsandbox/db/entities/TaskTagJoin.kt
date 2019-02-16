package com.awesome.zach.jotunheimrsandbox.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(
    tableName = "task_tag_join_table",
    foreignKeys = [
        ForeignKey(
            entity = Task::class,
            parentColumns = ["taskId"],
            childColumns = ["taskId"]
        ),
        ForeignKey(
            entity = Tag::class,
            parentColumns = ["tagId"],
            childColumns = ["tagId"]
        )]
)
data class TaskTagJoin(
    @PrimaryKey(autoGenerate = true)
    val taskTagJoinId: Long? = null,
    @NonNull
    val taskId: Long,
    @NonNull
    val tagId: Long
)