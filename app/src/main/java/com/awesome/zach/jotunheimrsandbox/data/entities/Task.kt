package com.awesome.zach.jotunheimrsandbox.data.entities

import androidx.annotation.NonNull
import androidx.room.*
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
        indices = [Index("taskId"), Index("projectId")],
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
    var projectId: Long,
    @ColumnInfo(name = "projectName")
    var projectName: String? = null,
    var isSelected: Boolean = false)