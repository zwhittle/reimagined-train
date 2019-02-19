package com.awesome.zach.jotunheimrsandbox.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.annotation.NonNull
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

@Entity(tableName = "task_table",
        indices = [Index("taskId")],
        foreignKeys = [ForeignKey(entity = Project::class,
                                  parentColumns = ["projectId"],
                                  childColumns = ["projectId"])])
data class Task(
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0,
    @NonNull
    var name: String,
    var date_start: LocalDate? = null,
    var date_end: LocalDate? = null,
    @NonNull
    var completed: Boolean = false,
    @NonNull
    var priority: Int = 1,
    @NonNull
    var projectId: Long)