package com.awesome.zach.jotunheimrsandbox.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import java.time.LocalDate

/**
 * Task @Entity
 * Tasks are user generated
 * Tasks are assigned a project (defaults to Inbox)
 *
 * Columns: taskId(PK), name, date_start, date_end, projectId(FK)
 * Null allowed: taskId(PK), date_start, date_end
 * ForeignKeys: projectId (Project: projectId)
 */

@Entity(
    tableName = "task_table",
    indices = [Index("taskId")],
    foreignKeys = [ForeignKey(
        entity = Project::class,
        parentColumns = ["projectId"],
        childColumns = ["projectId"]
    )]
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0,
    @NonNull
    var name: String,
    var date_start: LocalDate? = null,
    var date_end: LocalDate? = null,
    @NonNull
    var projectId: Long
)